TSI Racing
====

TSI Racing is a game in **Java 6**.
This is an Eclipse project.
The main goal was implement a framework, so the students could implement their bots (cars) to compete with each other.

I used the Java library: jpct. For more information visit: http://www.jpct.net/

The cars are implemented in Javascript. They must be in "cars" folder. There is a sample car in project.
The car can have a customized texture (samples in "textures" folder). It must be in the "cars" folder with the same car filename. The extension must be "jpg".
All cars in "cars" folder will participate in the race. After the last car finishes, the result will be stored in "result.txt". Only the cars that reach the end will appear in the result. It can runs slow because of the Javascript interpreter.

It can be run with arguments:
* **noscreen**: runs without GUI
* **speedX**: X is an int, from 1 to 4
* **trackY**: Y is an int, frmo 1 to 2

The default is: 3D GUI (JPCT), speed1 and track1.

The tracks are in "br.utfpr.gp.tsi.racing.track.res" folder. They are PNG files. White is water, black is road, red is start and green is curve. The road must have 1 pixel width (check the sample tracks). Can only be 1 red and the cars will start facing south. The green pixel must be: next to the road (not on the road), before the curve and on the same curve side. The green pixels will be used to calculate the distance until the curve and which side is the curve.

The ZIP file contains what the student needs to play the game. It runs on **Windows, Linux and Mac**.

Thanks to **Andres Jesse Porfirio** and **Diego Marczal** for helping me with this project.