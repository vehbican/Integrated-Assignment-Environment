using System;

public class BubbleSort {
    // Bubble sort algorithm
    public static void BubbleSortStrings(string[] arr) {
        int n = arr.Length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (String.Compare(arr[j], arr[j+1]) > 0) {
                    // swap arr[j] and arr[j+1]
                    string temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    public static void Main(string[] args) {
        // Get the list of strings from command line arguments
        string[] strArray = args;

        // Sort the list of strings using bubble sort
        BubbleSortStrings(strArray);

        // Print the sorted list of strings
        Console.WriteLine("Sorted list of strings:");
        foreach (string str in strArray) {
            Console.WriteLine(str);
        }
    }
}
