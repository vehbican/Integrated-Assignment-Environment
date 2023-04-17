import sys

# Bubble sort algorithm
def bubble_sort(arr):
    n = len(arr)
    for i in range(n):
        for j in range(0, n-i-1):
            if arr[j] > arr[j+1]:
                arr[j], arr[j+1] = arr[j+1], arr[j]

# Get the list of strings from command line arguments
str_list = sys.argv[1:]

# Sort the list of strings using bubble sort
bubble_sort(str_list)

# Print the sorted list of strings
print("Sorted list of strings:")
for s in str_list:
    print(s)
