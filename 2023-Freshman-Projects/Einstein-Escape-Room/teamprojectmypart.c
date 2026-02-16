#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <time.h>

int main() {
    printf("I successfully completed the first part and got some of my memories back.\n");
    while (getchar() != '\n');

    printf("And now I'm in room 2.\n");
    while (getchar() != '\n');

    printf("But, I need to choose which room I'm going to.\n");
    while (getchar() != '\n');

    printf("I have 5 room choices, but I need to be wise when I choose.\n");
    while (getchar() != '\n');

    printf("Because only 2 of them have a quiz inside.\n");
    while (getchar() != '\n');

    srand(time(NULL));
    int rooms[5];

    for (int i = 0; i < 2; ++i) {
        rooms[i] = 0;
    }

    for (int i = 2; i < 5; i++) {
        rooms[i] = rand() % 2;
    }

    printf("Room 1\n");
    printf("Room 2\n");
    printf("Room 3\n");
    printf("Room 4\n");
    printf("Room 5\n");

    int user_choice;
    printf("I need to make my choice: ");
    (void)scanf("%d", &user_choice);

    if (user_choice >= 1 && user_choice <= 5) {
        int attempts = 0;

        while (attempts < 4) {
            if (rooms[user_choice - 1] == 1) {
                printf("I'm inside the quiz room!\n");
                char correct_answer[] = "beautiful";
                char user_answer[15];

                do {
                    printf("Question: What is the most beautiful plant in the world?\n");
                    printf("Answer: ");
                    (void)scanf("%s", user_answer);

                    if (strcmp(user_answer, correct_answer) == 0) {
                        printf("I'm right! I got one of my memories back!\n");
                        while (getchar() != '\n');
                        printf("I went to the year 1664 to meet Newton.\n");
                        while (getchar() != '\n');
                        return 0;
                    }
                    else {
                        printf("Oh no! I'm wrong!\n");
                    }
                } while (strcmp(user_answer, correct_answer) != 0);
            }
            else {
                attempts++;
                printf("Oh no! There is nothing in this room! I have %d attempts left.\n", 4 - attempts);
                printf("Choose another room: ");
                (void)scanf("%d", &user_choice);
             
            }
        } 
        printf("You've run out of attempts. Game over. \n");
        return 1;
    }
    else {
        printf("Invalid room choice.Game over.\n");
        return 1;
    }

    printf("Now I'm in room 3. I need to get more of my memories back!\n");
    while (getchar() != '\n');

    printf("But, I need to choose again which room I'm going to.\n");
    while (getchar() != '\n');

    printf("Here have 7 room choices, I need to be wise when I choose.\n");
    while (getchar() != '\n');

    printf("Because only 3 of them have a quiz inside.\n");
    while (getchar() != '\n');

    srand(time(NULL)+1);
    int third_rooms[7];

    for (int i = 0; i < 3; ++i) {
        third_rooms[i] = 0; 
    }

    for (int i = 3; i < 7; i++) {
        third_rooms[i] = rand() % 2; 
    }

    printf("Room 1\n");
    printf("Room 2\n");
    printf("Room 3\n");
    printf("Room 4\n");
    printf("Room 5\n");
    printf("Room 6\n");
    printf("Room 7\n");

    int second_u_choice;
    printf("I need to make my choice: ");
    (void)scanf("%d", &second_u_choice); 

    if (second_u_choice >= 1 && second_u_choice <= 7) { 
        int attempts = 0;

        while (attempts < 4) {
            if (third_rooms[second_u_choice - 1] == 1) {  
                printf("I'm inside the quiz room!\n");
                char sec_correct_answer[] = "staircase"; 
                char sec_user_answer[15]; 

                do {
                    printf("Question: What goes up and down, but never moves?\n");
                    printf("Answer: ");
                    (void)scanf("%s", sec_user_answer); 

                    if (strcmp(sec_user_answer, sec_correct_answer) == 0) { 
                        printf("I'm right! I got all of my memories back!\n");
                        while (getchar() != '\n');
                        printf("I escaped all rooms and now can go back!\n");
                        while (getchar() != '\n');
                        return 0;
                    }
                    else {
                        printf("Oh no! I'm wrong!\n");
                    }
                } while (strcmp(sec_user_answer, sec_correct_answer) != 0); 
            }
            else {
                attempts++;
                printf("Oh no! There is nothing in this room! I have %d attempts left.\n", 4 - attempts);
                printf("Choose another room: ");
                (void)scanf("%d", &second_u_choice);   
            }
        }
        printf("You've run out of attempts. Game over. \n");
    }
    else {
        printf("Invalid room choice.Game over.\n");
    }

    printf("Through searching for memories and conversations with Newton, he achieved great achievements that will remain in the world.");
    printf("This is a work of fiction. Has nothing to do with historical facts.");
    return 0;
}
