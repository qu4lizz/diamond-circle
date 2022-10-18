# DiamondCircle

A 2-4 player board card game simulation

## Description

The game _Diamond Circle_ is a simulation in which figures move on fields of 7x7, 8x8, 9x9, or 10x10. The user needs to choose the number of players, give them a name, and choose the size of the field on which the simulation will be executed. Each player gets 4 random figures at the start. The player draws a number (1–4) or special card. If a number card is drawn out, the figure moves to the number of fields specified on the card; otherwise, if a special card is drawn out, there will be a generated random number of holes (between zero and the dimension of the matrix). After drawing out the card and executing the move, the next player gets to draw the card. There are three types of figures: walking (ordinary), flying (can't fall into a hole), and running (moves double the number of fields). There is also a ghost figure moving in the background and generating a random number of diamonds (between 2 and the matrix dimension) on the field, which figures can pick up and gain bonus movement for their next move. The figure ends its movement when it either reaches the final field (center of the matrix) or falls into a hole. The game ends when all the figures finish their movements.

## Challenge

The biggest challenges in this project were threads and the GUI. OOP concepts such as inheritance, polymorphism, and encapsulation were met by this project.


## Credits

Hole icon: <a href="https://www.flaticon.com/free-icons/hole" title="hole icons">Hole icons created by Freepik - Flaticon</a>
Walking figure icon: <a href="https://www.flaticon.com/free-icons/man" title="man icons">Man icons created by DinosoftLabs - Flaticon</a>
Flying figure icon: <a href="https://www.flaticon.com/free-icons/action-figure" title="action figure icons">Action figure icons created by Freepik - Flaticon</a>
Diamond icon: <a href="https://www.flaticon.com/free-icons/diamond" title="diamond icons">Diamond icons created by Freepik - Flaticon</a>
Joker icon: <a href="https://www.clipartmax.com/middle/m2i8b1K9m2N4N4m2_jester-or-joker-cartoon-illustration-stock-photo-black-and-white-card-joker/" target="_blank">Jester Or Joker Cartoon Illustration Stock Photo - Black And White Card Joker @clipartmax.com</a>
