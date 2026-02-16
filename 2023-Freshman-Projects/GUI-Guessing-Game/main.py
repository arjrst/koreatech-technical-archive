from tkinter import Tk, Label, Entry, Button, messagebox
import random
import time

window = Tk()
window.title("Guessing Game")

# Global variables
score = 0
tries_left = 0
start_time = 0
time_limit = 0
target_number = 0

def play_game():
    global score, tries_left, target_number, start_time, time_limit

    name = entry_name.get()
    difficulty = entry_difficulty.get()

    if difficulty.lower() == "easy":
        start = 0
        end = 30
        tries_allowed = 8
        time_limit = 30
    elif difficulty.lower() == "medium":
        start = 0
        end = 50
        tries_allowed = 6
        time_limit = 25
    elif difficulty.lower() == "hard":
        start = 0
        end = 100
        tries_allowed = 4
        time_limit = 20
    elif difficulty.lower() == "custom":
        try:
            start = int(entry_start.get())
            end = int(entry_end.get())
            tries_allowed = int(entry_tries.get())
            time_limit = int(entry_time.get())
        except ValueError:
            label_result["text"] = "Invalid custom inputs."
            return
    else:
        label_result["text"] = "Invalid choice. Please select a valid option."
        return

    # Clear previous messages
    label_result["text"] = ""
    
    # Update UI for game start
    label_guess.config(text=f"Hello, {name}! Guess a number between {start} and {end}.")
    
    target_number = random.randint(start, end)
    tries_left = tries_allowed
    start_time = time.time()
    
    # Update check button command
    button_check.config(command=check_guess)

def check_guess():
    global score, tries_left

    try:
        guess_input = int(entry_guess.get())
    except ValueError:
        label_result["text"] = "Please enter a number."
        return

    elapsed_time = time.time() - start_time

    if elapsed_time > time_limit:
        label_result["text"] = "Time is up! You took too long to guess."
        ask_play_again()
        return

    tries_left -= 1

    if guess_input == target_number:
        label_result["text"] = "Congratulations! You won!"
        score += 1
        label_score.config(text=f"Score: {score}")
        ask_play_again()
    elif guess_input > target_number:
        label_result["text"] = f"Tries left: {tries_left}\nIt's high!"
    else:
        label_result["text"] = f"Tries left: {tries_left}\nIt's low!"

    if tries_left == 0 and guess_input != target_number:
        label_result["text"] = "You run out of guesses! You lose."
        ask_play_again()

    entry_guess.delete(0, "end")

def ask_play_again():
    response = messagebox.askquestion("Play Again", "Would you like to play again?")
    if response == "yes":
        label_guess.config(text="")
        label_result.config(text="")
        entry_guess.delete(0, "end")
        # We don't call play_game() here to avoid recursion loop; 
        # user can click Play button again to restart with new settings.
    else:
        messagebox.showinfo("Game Over", f"Thank you for playing! Your score is {score}.")
        window.destroy()

# UI Widgets (Exact Order from Original)
label_welcome = Label(window, text="Welcome to the Guessing Game!")
label_welcome.pack(pady=10)

label_name = Label(window, text="Please enter your name:")
label_name.pack()

entry_name = Entry(window)
entry_name.pack()

label_difficulty = Label(window, text="Choose a difficulty level (easy, medium, hard, custom):")
label_difficulty.pack()

entry_difficulty = Entry(window)
entry_difficulty.pack()

label_start = Label(window, text="Enter the start value (for custom):")
label_start.pack()

entry_start = Entry(window)
entry_start.pack()

label_end = Label(window, text="Enter the end value (for custom):")
label_end.pack()

entry_end = Entry(window)
entry_end.pack()

label_tries = Label(window, text="Enter the number of tries (for custom):")
label_tries.pack()

entry_tries = Entry(window)
entry_tries.pack()

label_time = Label(window, text="Enter the time limit (for custom):")
label_time.pack()

entry_time = Entry(window)
entry_time.pack()

button_play = Button(window, text="Play", command=play_game)
button_play.pack(pady=10)

label_guess = Label(window, text="")
label_guess.pack()

entry_guess = Entry(window)
entry_guess.pack()

button_check = Button(window, text="Check")
button_check.pack(pady=10)

label_result = Label(window, text="")
label_result.pack()

label_score = Label(window, text="")
label_score.pack()

window.mainloop()