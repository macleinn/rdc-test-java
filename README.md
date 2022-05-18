# RDC test
## Question 1
You are given the task of implementing a cache micro-service.

The cache micro-service has 3 requirements:
1. Store a key-value pair to the service. Both the key and value are in string, not numbers.
2. Get the value using the key.
3. Every value stored will become invalid after 10 seconds and is deleted automatically.

Your task:
* Implement the cache mentioned above with a SpringBoot micro-service and expose the Web(HTTP) APIs.
* Pay attention to the concurrent issues.
* Time complexity for Store() should be less than O(n), Get() in O(1) as we are heavy in reading, and for Expire() could be in the level of O(n) but please optimize it as much as possible(Means the Big Theta Θ should be less than Θ(n)).
* Consider large amount of data being stored, e.g. 1000M items.
* We'd like to see your own implementation with Java SDK. Don't use any third-party cache service or existing open source implementation.

Bonus task:

The client of this micro-service could specify the expiration period for each individual key-value pair, with the default expiration period is 10.

## Question 2
We'd like to have a service to do some special calculation for our company. One of the functions in this library is called tripletsZero which can:
1. Receive a series of numbers.
2. Return all unique triplets with the sum of zero (x+y+z=0) in these numbers.    

Example:
```
input = [-1, 0, 1, 2, -1, -4],
A solution set is : [[-1, 0, 1],[-1, -1, 2]]
```

Your task:
* Implement the function tripletsZero in a Springboot service.
* The solution set must not contain duplicate triplets.
* Time complexity should be less or equal to O(N²)
* Write unit tests for it.
