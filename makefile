all: build run

run:
	- cd ./out && java App && cd ..

build:
	- rm -r ./out
	- mkdir out
	- cd ./src && javac -d ../out/ ./*.java && cd ..
	- mkdir ./out/assets/
	- cp -r ./assets/images ./out/assets/images
