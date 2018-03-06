// Created by Ashleyz
    // http://www.github.com/Anthonyrules144

/// <reference path='../index.d.ts'/>
// This makes the Visual Studio Code app initialize TypeScript
// This also works in Atom if you have the Atom-TypeScript package
// I do this because of error testing. Would you rather look for each and every function yourself?
// As an alternative, you can use Engine.getMethodsOfClass(object);


var main = function() {
    // Character
    Character.create("assets/models/untitled.obj");
    Character.setFov(120);
    Character.setHealth(99); // by default it's 100.

    // Models
    Models.create(Engine.NewFloat(150, -5, 0), 500, 0.5, 30, new Material(ColorAttribute.createDiffuse(Color.BLACK)), null);

    // Creating textures
    var clickableTexture1 = Textures.addTexture(2, "assets/sprites/Ant-2-1.png")
        .setActive(true)
        .setPosition(100, 100)
        .setSize(150, 250)
        .setDepth(0);

    // Textures with interaction
    clickableTexture1.onHover(true /*This shows that it WAS hovered*/, new Runnable(function() {
        clickableTexture1.setTexture("assets/sprites/Ant-2-2.png");
    })).onHover(false /*This shows that it stopped being hovered*/, new Runnable(function() {
        clickableTexture1.setTexture("assets/sprites/Ant-2-1.png");
    }));

    // Songs / Media
    var sound = Sound.newSound("content/sounds/song.mp4");
    //var sound_time = sound.mediaPlayer.getDuration().toMinutes();
    //sound.mediaPlayer.setVolume(5);
    //sound.mediaPlayer.play();

    // External console
    Console.open_external();

    // Mouse wheel
    Input.onMouseWheelUp(new Runnable(function() {
        Console.log(Camera.fieldOfView -= 1);
    }));
    Input.onMouseWheelDown(new Runnable(function() {
        Console.log(Camera.fieldOfView += 1);
    }));

    // Key pressing
    Input.onKeyPressed("W", new Runnable(function() {
        Console.log("pressed");
        clickableTexture1.setTexture("assets/sprites/Ant-2-2.png");
    }));
    Input.onKeyReleased("W", new Runnable(function() {
        Console.log("released");
        clickableTexture1.setTexture("assets/sprites/Ant-2-1.png");
    }));

    Input.onKeyPressed("`", new Runnable(function() {
        
    }));

    // LAN networking
    Console.warn("Open network IPs connected to: \n" + Networking.getNetworks());

    // Dialogs
    Dialog.showConfirmDialog("hi", "bye", Dialog.YES_NO_OPTION);
    var resultFromInput = Dialog.showInputDialog("Title", "Message", Dialog.YES_OPTION);
    Console.warn("Result: " + resultFromInput);

    // You can make the main function return whatever you want back to java.
    return "blorph blam bloop";
};

// Creating a Health Bar at the top
Textures.addTexture(1, "assets/sprites/ui/black.png")
    .setActive(true)
    .setPosition(Window.getWidth()-127, Window.getHeight()-28)
    .setSize(104, 20)
    .setDepth(0);
var healthBar = Textures.addTexture(0, "assets/sprites/ui/green.png")
    .setActive(true)
    .setPosition(Window.getWidth()-125, Window.getHeight()-25)
    .setSize(100, 15)
    .setDepth(1);
// Creating text next to said health bar.
var healthText = ScreenText.addScreenText(0, "DroidSerif")
    .setColor(Color.RED)
    .setScale(1.25)
    .setText("Test")
    .setBold(true)
    .setPosition(Window.getWidth()-125-50, Window.getHeight()-12);

// Getting methods / functions of a class or variable
Console.log(Engine.getMethodsOfClass(healthText));

// Character health listener
Character.onHealthChanged(new Runnable(function() {
    if(Character.getHealth() < 0)
        return;
    if(Character.getHealth() > 100) {
        healthBar.setSize(100, 15);
        return null;
    } else if(Character.getHealth() <= 0) {
        healthBar.setSize(0, 15);
        return null;
    }
    healthText.setText(Character.getHealth() + "%");
    healthBar.setSize(Character.getHealth(), 15);
}));


// Timers
Timer.start(350, 0, new Runnable(function() {
    if(Character.getHealth() > 0)
        Character.damage(5);
}));
