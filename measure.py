import subprocess
import time
import random
import numpy as np
import matplotlib.pyplot as plt

random.seed(42)

def run_and_measure(command, param1, param2):
    # Append parameters to the command list
    full_command = command + [param1, param2]
    start_time = time.time()  # Start time measurement
    process = subprocess.Popen(full_command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    stdout, stderr = process.communicate()
    end_time = time.time()  # End time measurement
    
    elapsed_time = (end_time - start_time) * 1000  # Convert to milliseconds
    
    if stderr:
        print(stderr)
    if stdout:
        print(stdout)

    return elapsed_time

# Function to compile and run C program
def run_c(param1, param2):
    subprocess.run(["gcc", "main.c", "-o", "main.out"], check=True)
    return run_and_measure(["./main.out"], param1, param2)

# Function to compile and run C++ program
def run_cpp(param1, param2):
    subprocess.run(["g++", "main.cpp", "-o", "main.out"], check=True)
    return run_and_measure(["./main.out"], param1, param2)

# Function to compile and run Java program
def run_java(param1, param2):
    subprocess.run(["javac", "main.java"], check=True)
    return run_and_measure(["java", "main"], param1, param2)

# Function to run JavaScript program
def run_js(param1, param2):
    return run_and_measure(["node", "main.js"], param1, param2)

# Function to compile and run Rust program
def run_rs(param1, param2):
    subprocess.run(["rustc", "main.rs", "-C", "opt-level=3","-o", "main.out"], check=True)
    return run_and_measure(["./main.out"], param1, param2)

def run_all(sequences):
    # Initialize lists to store time and memory for each language
    times_c    = []
    times_cpp  = []
    times_java = []
    times_js   = []
    times_rs   = []

    # Run tests for each pair of sequences
    for seq1 in sequences:
        for seq2 in sequences:
            if seq1 != seq2:  # Avoid comparing the sequence with itself
                time_c    = run_c(seq1, seq2)
                time_cpp  = run_cpp(seq1, seq2)
                time_java = run_java(seq1, seq2)
                time_js   = run_js(seq1, seq2)
                time_rs   = run_rs(seq1, seq2)

                # Append the time and memory to the lists
                times_c.append(time_c)
                times_cpp.append(time_cpp)
                times_java.append(time_java)
                times_js.append(time_js)
                times_rs.append(time_rs)

    # Combine the time data into a single list of lists
    plt.rcParams.update({'font.size': 18})
    times_data = [times_c, times_cpp, times_java, times_js, times_rs]

    # Create the box plot
    plt.figure(figsize=(10, 6))
    plt.boxplot(times_data)

    # Adding labels and title
    plt.title('Comparação dos Tempos de Execução')
    plt.xticks([1, 2, 3, 4, 5], ['C', 'C++', 'Java', 'JavaScript', 'Rust'])
    plt.ylabel('Tempo (ms)')

    # Show the plot
    plt.savefig("time.png")

    times = [np.mean(times_c), np.mean(times_cpp), np.mean(times_java), np.mean(times_js), np.mean(times_rs)]
    languages = ['C', 'C++', 'Java', 'JavaScript', 'Rust']
    colors = ['lightblue', 'blue', 'red', 'yellow', 'darkorange']  # Colors for each language
    language_labels = ['C', 'C++', 'Java', 'JavaScript', 'Rust']

    # Create the bar plot
    plt.figure(figsize=(10, 6))
    plt.bar(languages, times, color=colors)

    # Adding labels and title in Portuguese
    plt.title('Comparação dos Tempos Médios de Execução')
    plt.xticks(range(len(languages)), language_labels)
    plt.ylabel('Tempo (ms)')

    # Show the plot
    plt.savefig("time_bar.png")

def read_string_from_file(file_path):
    with open(file_path, 'r') as file:
        # Read lines from the file and concatenate them into a single string
        single_string = ''.join(line.strip() for line in file)
    return single_string

file_path = 'CALM1.txt' # CALM1 calmodulin 1 [ Homo sapiens (human) ]
n_sequences = 10
base_string = read_string_from_file(file_path)

def partition_string(s, n):
    if n <= 0:
        raise ValueError("Number of parts must be positive")
    if n == 1:
        return [s]  # Only one partition which is the string itself
    if n >= len(s):
        return list(s)  # Each character becomes a partition

    # Generate n-1 random partition indices
    partition_indices = sorted(random.sample(range(1, len(s)), n - 1))
    partitions = []
    previous_index = 0

    # Slice the string into partitions
    for index in partition_indices:
        partitions.append(s[previous_index:index])
        previous_index = index
    partitions.append(s[previous_index:])  # Add the last partition

    return partitions

sequences = partition_string(base_string, n_sequences)

run_all(sequences)

            
