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

## Updates

**21/11/2018**

- Move buttons to the top for real test in order to improve user experience using virtual keyboard

- Add dialog after clicking "start" or "test" to remind not to skip the instruction given before moving to type test

- Set the software fixed to full screen and not resizable, to minimize the influence from other parts of the screen

- For virtual keyboard, the words generated will be give on the top of the screen, so that the words will not be covered 
by keyboard (physical keyboard mode stays unchanged)

**24/11/2018**

- Move to buttons to the top for the page "Before test"

- Set the text area get focus automatically when opening typing test page

**25/11/2018**

- Change some words in words.txt to British spelling

- Improve algorithm to count words input amount and blank space handling (Now you can split two words with multiple space
but will only be counted as two words)