from pymongo import MongoClient
from bson.objectid import ObjectId
import importlib
import crud_operations  # leave reference because this was used in Jupyter Notebookes

importlib.reload(crud_operations)


class AnimalShelter:
    """CRUD operations for Animal collection in MongoDB"""

    def __init__(self, username, password, host="localhost", port=27017, db="AAC", collection="animals"):
        USER = 'aacuser'
        PASS = 'SNHU1234'
        HOST = 'nv-desktop-services.apporto.com'
        PORT = 34763
        DB = 'AAC'
        COL = 'animals'

        # Initialize the MongoClient to connect to the MongoDB instance
        self.client = MongoClient(f'mongodb://{USER}:{PASS}@{HOST}:{PORT}')
        self.database = self.client[DB]
        self.collection = self.database[COL]

    def create(self, data):
        """Insert a document into the collection."""
        if data:
            insert_result = self.collection.insert_one(data)
            return insert_result.acknowledged  # Return True if successful
        return False

    def read(self, query={}, limit=0):
        """Query documents from the collection."""
        if query is None:
            query = {}

        cursor = self.collection.find(query)
        if limit > 0:
            cursor = cursor.limit(limit)

        result = [document for document in cursor]
        return result

    def update(self, query, update_data, multiple=False):
        """Update document(s) in the collection based on a query."""
        if query and update_data:
            if multiple:
                update_result = self.collection.update_many(query, {"$set": update_data})
            else:
                update_result = self.collection.update_one(query, {"$set": update_data})
            return update_result.modified_count  # Return the count of modified documents
        return 0

    def delete(self, query, multiple=False):
        """Delete document(s) from the collection based on a query."""
        if query:
            if multiple:
                delete_result = self.collection.delete_many(query)
            else:
                delete_result = self.collection.delete_one(query)
            return delete_result.deleted_count  # Return the count of deleted documents
        return 0

    # NEW ENHANCED OPERATIONS

    def count_records(self, criteria=None):
        """
        Count records matching the given criteria.

        Args:
            criteria (dict): MongoDB query criteria. If None, counts all records.

        Returns:
            int: Count of matching records
        """
        if criteria is None:
            criteria = {}

        try:
            # Using the count_documents method for optimized counting
            return self.collection.count_documents(criteria)
        except Exception as e:
            print(f"Error counting records: {e}")
            return 0

    def create_multiple(self, records):
        """
        Insert multiple documents into the collection.

        Args:
            records (list): List of documents to insert

        Returns:
            tuple: (bool success, int count) - Success status and count of inserted documents
        """
        if not records or not isinstance(records, list):
            return False, 0

        try:
            # Validate each record in the list
            valid_records = [record for record in records if record]

            if not valid_records:
                return False, 0

            # Insert multiple documents
            insert_result = self.collection.insert_many(valid_records)
            return True, len(insert_result.inserted_ids)
        except Exception as e:
            print(f"Error inserting multiple records: {e}")
            return False, 0

    def update_multiple(self, records):
        """
        Update multiple documents based on criteria in each record.

        Args:
            records (list): List of dicts with format [{'query': {...}, 'update_data': {...}}]

        Returns:
            tuple: (bool success, int count) - Success status and count of updated documents
        """
        if not records or not isinstance(records, list):
            return False, 0

        try:
            total_updated = 0

            # Process each update operation
            for record in records:
                if 'query' in record and 'update_data' in record:
                    query = record['query']
                    update_data = record['update_data']

                    if query and update_data:
                        update_result = self.collection.update_many(
                            query,
                            {"$set": update_data}
                        )
                        total_updated += update_result.modified_count

            return True, total_updated
        except Exception as e:
            print(f"Error updating multiple records: {e}")
            return False, 0

    def delete_multiple(self, queries):
        """
        Delete multiple documents based on multiple queries.

        Args:
            queries (list): List of query criteria, each will be used to delete matching documents

        Returns:
            tuple: (bool success, int count) - Success status and count of deleted documents
        """
        if not queries or not isinstance(queries, list):
            return False, 0

        try:
            total_deleted = 0

            # Process each delete operation
            for query in queries:
                if query:
                    delete_result = self.collection.delete_many(query)
                    total_deleted += delete_result.deleted_count

            return True, total_deleted
        except Exception as e:
            print(f"Error deleting multiple records: {e}")
            return False, 0
