public class main {
    // Bubble sort algorithm
    public static void bubbleSort(String[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].compareTo(arr[j+1]) > 0) {
                    // swap arr[j] and arr[j+1]
                    String temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        // Get the list of strings from command line arguments
        String[] strArray = args;

        // Sort the list of strings using bubble sort
        bubbleSort(strArray);

        // Print the sorted list of strings
        System.out.println("Sorted list of strings:");
        for (String str : strArray) {
            System.out.println(str);
        }
    }
}
