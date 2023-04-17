#include <stdio.h>
#include <string.h>

#define MAX_LEN 100 // Maximum length of a string

int main(int argc, char *argv[]) {
    int i, j;
    char temp[MAX_LEN];

    // Bubble sort algorithm
    for (i = 1; i < argc; i++) {
        for (j = 1; j < argc - i; j++) {
            if (strcmp(argv[j], argv[j + 1]) > 0) {
                strcpy(temp, argv[j]);
                strcpy(argv[j], argv[j + 1]);
                strcpy(argv[j + 1], temp);
            }
        }
    }

    // Print the sorted list of strings
    printf("Sorted list of strings:\n");
    for (i = 1; i < argc; i++) {
        printf("%s\n", argv[i]);
    }

    return 0;
}
