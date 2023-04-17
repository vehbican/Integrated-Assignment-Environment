#include <iostream>
#include <string>

using namespace std;

// Bubble sort algorithm
void bubbleSort(string arr[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j+1]) {
                // swap arr[j] and arr[j+1]
                string temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
    }
}

int main(int argc, char *argv[]) {
    // Get the list of strings from command line arguments
    string strArray[argc-1];
    for (int i = 1; i < argc; i++) {
        strArray[i-1] = argv[i];
    }

    // Sort the list of strings using bubble sort
    bubbleSort(strArray, argc-1);

    // Print the sorted list of strings
    cout << "Sorted list of strings:" << endl;
    for (int i = 0; i < argc-1; i++) {
        cout << strArray[i] << endl;
    }

    return 0;
}
