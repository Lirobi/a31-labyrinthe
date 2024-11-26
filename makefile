all: build run

run:
	- cd ./out && java App && cd ..

build:
	- cd ./src && javac -d ../out/ ./*.java && cd ..