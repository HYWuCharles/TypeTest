# TypeTest
## Basic info
This software is written by Haoyu Wu

**Platform**

    Mac OS X
    Windows
    Linux (theoretically)

If used in research, please cite me :)

Contact information yl18277@bristol.ac.uk

This software can test:

- Time consumed

- Total letters input

- Total backspace input

- Total correct words input

- Total incorrect words input

- Keyboard keys stroke rate

- Correct ratio 

- Correct words input rate

- Words input rate (includes wrong words)

## Usage
    java -jar Typetest.jar

**Default save space**: Your_jar_file_directory/save

**You can change this save space by selecting new path**

Result will be saved into two files

    save/pkdata.txt

This is the data collected under using physical keyboards condition, mode code will be 0

    save/tkdata.txt
    
This is the data collected under using touch keyboards condition, mode code will be 1

You will need a participant number for test, if you are not in a real test, just give yourself a lovely code :) It will
not collect any user data

## Words

This application contains 999 most common English words, for every test, it randomly generates 100 words (with duplicates)
