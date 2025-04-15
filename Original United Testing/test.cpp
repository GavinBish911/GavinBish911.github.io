#include "gtest/gtest.h"

// the global test environment setup and tear down
// you should not need to change anything here
class Environment : public ::testing::Environment
{
public:
  ~Environment() override {}

  // Override this to define how to set up the environment.
  void SetUp() override
  {
    //  initialize random seed
    srand(time(nullptr));
  }

  // Override this to define how to tear down the environment.
  void TearDown() override {}
};

// create our test class to house shared data between tests
// you should not need to change anything here
class CollectionTest : public ::testing::Test
{
protected:
  // create a smart point to hold our collection
  std::unique_ptr<std::vector<int>> collection;

  void SetUp() override
  { // create a new collection to be used in the test
    collection.reset( new std::vector<int>);
  }

  void TearDown() override
  { //  erase all elements in the collection, if any remain
    collection->clear();
    // free the pointer
    collection.reset(nullptr);
  }

  // helper function to add random values from 0 to 99 count times to the collection
  void add_entries(int count)
  {
    assert(count > 0);
    for (auto i = 0; i < count; ++i)
      collection->push_back(rand() % 100);
  }
};

// When should you use the EXPECT_xxx or ASSERT_xxx macros?
// Use ASSERT when failure should terminate processing, such as the reason for the test case.
// Use EXPECT when failure should notify, but processing should continue

// Test that a collection is empty when created.
// Prior to calling this (and all other TEST_F defined methods),
//  CollectionTest::StartUp is called.
// Following this method (and all other TEST_F defined methods),
//  CollectionTest::TearDown is called
TEST_F(CollectionTest, CollectionSmartPointerIsNotNull)
{
  // is the collection created
  ASSERT_TRUE(collection);

  // if empty, the size must be 0
  ASSERT_NE(collection.get(), nullptr);
}

// Test that a collection is empty when created.
TEST_F(CollectionTest, IsEmptyOnCreate)
{
  // is the collection empty?
  ASSERT_TRUE(collection->empty());

  // if empty, the size must be 0
  ASSERT_EQ(collection->size(), 0);
}

/* Comment this test out to prevent the test from running
 * Uncomment this test to see a failure in the test explorer */
//TEST_F(CollectionTest, AlwaysFail)
//{
//  FAIL();
//}

// Create a test to verify adding a single value to an empty collection
TEST_F(CollectionTest, CanAddToEmptyVector)
{
  // Start empty
  ASSERT_TRUE(collection->empty());
  ASSERT_EQ(collection->size(), 0);

  // Add a single value
  add_entries(1);

  // Verify collection is no longer empty
  ASSERT_FALSE(collection->empty());
  ASSERT_EQ(collection->size(), 1);
}

// Create a test to verify adding five values to collection
TEST_F(CollectionTest, CanAddFiveValuesToVector)
{
  ASSERT_TRUE(collection->empty());
  add_entries(5);
  ASSERT_EQ(collection->size(), 5);
}

// Create a test to verify that max size is greater than or equal to size for 0, 1, 5, 10 entries
TEST_F(CollectionTest, MaxSizeIsGreaterThanOrEqualToSize)
{
  for (int count : {0, 1, 5, 10})
  {
    collection->clear();
    add_entries(count);

    ASSERT_LE(collection->size(), collection->max_size());
  }
}

// Create a test to verify that capacity is greater than or equal to size for 0, 1, 5, 10 entries
TEST_F(CollectionTest, CapacityIsGreaterThanOrEqualToSize)
{
  for (int count : {0, 1, 5, 10})
  {
    collection->clear();
    add_entries(count);

    ASSERT_GE(collection->capacity(), collection->size());
  }
}




// Create a test to verify resizing increases the collection
TEST_F(CollectionTest, ResizingIncreasesCollection)
{
  collection->resize(5);
  ASSERT_EQ(collection->size(), 5);
}

// Create a test to verify resizing decreases the collection
TEST_F(CollectionTest, ResizingDecreasesCollection)
{
  add_entries(10);
  ASSERT_EQ(collection->size(), 10);

  collection->resize(5);
  ASSERT_EQ(collection->size(), 5);
}

// Create a test to verify resizing decreases the collection to zero
TEST_F(CollectionTest, ResizingToZeroClearsCollection)
{
  add_entries(10);
  ASSERT_EQ(collection->size(), 10);

  collection->resize(0);
  ASSERT_TRUE(collection->empty());
}

// Create a test to verify clear erases the collection
TEST_F(CollectionTest, ClearErasesCollection)
{
  add_entries(10);
  ASSERT_EQ(collection->size(), 10);

  collection->clear();
  ASSERT_TRUE(collection->empty());
}

// Create a test to verify erase(begin,end) erases the collection
TEST_F(CollectionTest, EraseBeginToEnd)
{
  add_entries(10);
  ASSERT_EQ(collection->size(), 10);

  collection->erase(collection->begin(), collection->end());
  ASSERT_TRUE(collection->empty());
}

// Create a test to verify reserve increases the capacity but not the size of the collection
TEST_F(CollectionTest, ReserveIncreasesCapacityButNotSize)
{
  size_t initial_capacity = collection->capacity();
  collection->reserve(initial_capacity + 10);

  ASSERT_GT(collection->capacity(), initial_capacity);
  ASSERT_EQ(collection->size(), 0);
}

// Create a test to verify the std::out_of_range exception is thrown when calling at() with an index out of bounds
TEST_F(CollectionTest, OutOfRangeThrowsException)
{
  add_entries(5); // Add 5 elements to collection

  // Accessing an index that is out of range
  ASSERT_THROW(collection->at(10), std::out_of_range);
}

// Create your own positive test: Verify that the collection can hold duplicate values
TEST_F(CollectionTest, CanHoldDuplicateValues)
{
  collection->push_back(10);
  collection->push_back(10); // Duplicate value

  ASSERT_EQ(collection->size(), 2);
  ASSERT_EQ(collection->at(0), 10);
  ASSERT_EQ(collection->at(1), 10);
}

// Create your own negative test: Verify trying to access an empty collection throws an exception
TEST_F(CollectionTest, AccessEmptyCollectionThrowsException)
{
  ASSERT_THROW(collection->at(0), std::out_of_range); // Accessing empty collection
}


//Use this to verify that MaxSize was  extremely high and it was so I am used this information to tweak CapacityisGreaterThan and MaxSizeisGreaterThan
//as I thought my failure was do to programming but it was catching correctly and it should have been aborted because it was out of range
TEST_F(CollectionTest, MaxSizeIsSane)
{
  // Sanity check to ensure max_size is large enough (but don't assume it's smaller than 1e9)
  std::cout << "max_size: " << collection->max_size() << std::endl;

  // Ensure max_size is at least a reasonably large number
  ASSERT_GT(collection->max_size(), 1000) 
      << "max_size() is unrealistically small. Potential library or platform issue.";

  // Adjust assertions to reflect platform limits
  // Avoid flagging as "absurd" unless the result makes no practical sense
  if (collection->max_size() > 1e12) {
    std::cout << "Warning: max_size is extremely large, as expected on some platforms "
              << "(e.g., large theoretical containers)." << std::endl;
  }
}

