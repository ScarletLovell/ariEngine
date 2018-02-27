
// --- functions --- //
declare function Runnable(Function): void;

// All Classes
interface jV {
    equals(Object): boolean;
    tostring(): string;
}

interface En {
    setTitle(string: "Title"): void;
    setWindow(Width: "number", Height: "number"): void;
}

interface Window {
    getWidth(): number;
    getHeight(): number;
} declare var Window: Window;

interface Engine extends En {
    getMethodsOfClass(object): string;
    getSuperClassOfClass(object): string;
    getTitle(): string;
    NewFloat(... number): FloatArray<number>;
    isPaused(): Boolean
} declare var Engine: Engine;

interface Console {
    getColor(ColorName: string): string;
    log(... object): void;
    warn(... object): void;
    error(... object): void;
    open_external(): void;
    close_external(): void;
} declare var Console: Console;

interface Timer {
    start(Delay: number, Ticks: number, Function): Timer;
    end(): void;
} declare var Timer: Timer;

interface inputClass {
    runnable; pressed; key;
    setInput(Runnable): inputClass;
    setPressType(Boolean): inputClass;
    remove();
}
interface Input {
    onKeyPressed(Key: string, Runnable): inputClass;
    onKeyJustPressed(Key: string, Runnable): inputClass;
    onKeyReleased(Key: string, Runnable): inputClass;
    onMouseWheelUp(Runnable): void;
    onMouseWheelDown(Runnable): void;
    getInputList(): string;
} declare var Input: Input;

interface Texture {
    new(Location: string): Texture;
    //getTextureData(): TextureData;
    getHeight(number): number;
    getWidth(number): number;
    toString(): string;
    dispose(): void;
    draw(Pixmap, x, y): void;
    getDepth(number);
    isManaged(Boolean): Boolean;
} declare var Texture: Texture;

interface textureClass {
    thisTexture: Texture;
    id: "number";

    onClick(Boolean, Runnable): textureClass;
    onHover(Boolean, Runnable): textureClass;
    get(): textureClass;
    setTexture(Texture): textureClass;
    getTexture(): Texture;
    setPosition(x: number, y: number): textureClass;
    setActive(Boolean): textureClass;
    setDepth(depth: number): textureClass;
    setSize(Width, Height): textureClass;
    getId(): "number";
    onCreate(Runnable): textureClass;
    clone(): textureClass;
    clone(string: "New Texture location"): textureClass;
    delete();
}

interface Textures {
    addTexture(id: number, file: string): textureClass;
} declare var Textures: Textures;

interface screenTextClass {
    id: "number";
    setPosition(x: number, y: number): screenTextClass;
    setText(string): screenTextClass;
    setColor(Color: Color): screenTextClass;
    setScale(Scale: number): screenTextClass;
    setBold(Boolean): screenTextClass;
    setItalic(Boolean): screenTextClass;
    delete(): void;
}
interface ScreenText {
    addScreenText(id: number): screenTextClass;
    addScreenText(id: number, font: string): screenTextClass;
} declare var ScreenText: ScreenText;

// --- models n stuff --- //

interface Vector3 {
    toString(): string;
    x: number;
    y: number;
    z: number;
    len(): number;
    set(val: number[]): Vector3;
    set(x: number, y: number, z: number): Vector3;
    set(Vector3): Vector3;
    add(number): Vector3;
    add(Vector3): Vector3;
    add(x: number, y: number, z: number): Vector3;
    clamp(min: number, max: number): Vector3;
    cpy(): Vector3;
    nor(): Vector3;
}

interface array<T> {
    items: T[];
    ordered: boolean;
    size: number;
}
interface ArrayMap<T0, T1> {

}
interface Matrix4 {
    M00: number;
    M01: number;
    M02: number;
    M03: number;
    M10: number;
    M11: number;
    M12: number;
    M13: number;
    M20: number;
    M21: number;
    M22: number;
    M23: number;
    M30: number;
    M31: number;
    M32: number;
    M34: number;
    val: number[];
}
interface NodeKeyFrame<T> {

}
interface Node {
    globalTransform: Matrix4;
    id: string;
    inheritTransform: boolean;
    isAnimated: boolean;
    localTransform: Matrix4;
    parent: Node;
    parts: array<NodePart>;
    scale: Vector3;
    translation: Vector3;
}
interface NodePart {
    equals(object: object): boolean;
    render(shader: ShaderProgram): void;
    render(shader: ShaderProgram, autoBind: boolean): void;
    set(other: MeshPart): MeshPart;
    set(id: string, mesh: Mesh, offset: number, size: number, type: number): MeshPart;
    update(): void;
    bones: Matrix4[];
    enabled: boolean;
    invBoneBindTransforms: ArrayMap<Node, Matrix4>;
    material: Material;
    meshPart: MeshPart;
}
interface NodeAnimation extends jV {
    node: Node;
}
interface BoundingBox {
    max: Vector3;
    min: Vector3;
}
declare class Material {
    constructor(ColorAttribute);
}
interface material extends jV {
    id: string;
    copy(): material;
    equals(object: object): boolean;
    hashCode(): number;
}
interface ShaderProgram {
    BINORMAL_ATTRIBUTE: string;
    BONEWEIGHT_ATTRIBUTE: string;
    COLOR_ATTRIBUTE: string;
    NORMAL_ATTRIBUTE: string;
    pedantic: boolean;
    POSITION_ATTRIBUTE: string;
    prependFragmentCode: string;
    TANGENT_ATTRIBUTE: string;
    TEXCOORD_ATTRIBUTE: string;
}
interface Mesh {
    bind(shader: ShaderProgram): void;
    bind(shader: ShaderProgram, locations: number[]);
    calculateBoundingBox(): BoundingBox;
    calculateBoundingBox(bbox: BoundingBox): void;
    calculateBoundingBox(out: BoundingBox, offset: number, count: number): BoundingBox;
    calculateBoundingBox(out: BoundingBox, offset: number, count: number, transform: Matrix4): BoundingBox;
    calculateRadius(centerX: number, centerY: number, centerZ: number): number;
    calculateRadius(centerX: number, centerY: number, centerZ: number, offset: number, count: number): number;
    calculateRadius(centerX: number, centerY: number, centerZ: number, offset: number, count: number, transform: Matrix4): number;
    calculateRadius(center: Vector3): number;
    calculateRadius(center: Vector3, offset: number, count: number): number;
    calculateRadius(center: Vector3, offset: number, count: number, transform: Matrix4): number;
    calculateRadiusSquared(centerX: number, centerY: number, centerZ: number, offset: number, count: number, transform: Matrix4): number;
    //clearAllMeshes(app: Application): void;
    copy(isStatic: boolean): Mesh;
    copy(isStatic: boolean, removeDuplicates: boolean, usage: number[]);
    dispose(): void;
}
interface MeshPart {
    center: Vector3;
    halfExtents: Vector3;
    id: string;
    mesh: Mesh;
    offset: number;
    primitiveType: number;
    radius: number;
    size: number;
}
interface Animation {
    duration: number;
    id: string;
    nodeAnimations: array<NodeAnimation>;
}
interface ModelInstance {
    animations: Array<Animation>;
}

interface Environment {

}
interface ModelCreation {
    setSize(float: number[]): ModelCreation;
    setWidth(float: number): ModelCreation;
    setHeight(float: number): ModelCreation;
    setDepth(float: number): ModelCreation;
    setMaterial(material: Material): ModelCreation;
    setEnvironment(env: Environment): ModelCreation;
    build(): ModelInstance;
}
interface Model {
    createBox(): ModelCreation;
} declare var Model: Model;
interface Entity {
    getPosition(): Vector3;
    setPosition(x: number, y: number, z: number);
    //getBoundingBox():
    create(string): Model;
} declare var Entity: Entity;

interface Camera {
    update(): void;
    lookAt(target: Vector3): void;
    lookAt(x: number, y: number, z: number): void;
    translate(Vector3): void;
    translate(x: number, y: number, z: number): void;
    fieldOfView: number;
    up: Vector3;
    position: Vector3;
    direction: Vector3;
    far: number;
    near: number;
    veiwportHeight: number;
    viewportWidth: number;
} declare var Camera: Camera;

interface Character extends Entity {
    create(): Character;
    camera: Camera;
    setFov(FoV: number);
    damage(number);
    heal(number);
    setHealth(number);
    getHealth(): number;
    onDamage(Runnable);
    onHealthChanged(Runnable);
} declare var Character: Character;

// --- network --- //

interface Networking {
    addresses: "HashMap";
    getNetworks(): string;
    getLocalNetworks(): string;
    connect(string): boolean;
} declare var Networking: Networking;

// --- color --- //

interface valueOf {
    r; g; b; a;
    hashCode(): "int";
    toFloatBits(): "float";
    toIntBits(): "int";
    add(Color): valueOf;
}
interface Color extends jV {
    valueOf(string: "hex"): valueOf;
    BLACK;
    BLUE;
    BROWN;
    CHARTREUSE;
    CLEAR;
    CORAL;
    CYAN;
    DARK_GRAY;
    FIREBRICK;
    FOREST;
    GOLD;
    GOLDRENROD;
    GRAY;
    GREEN;
    LIGHT_GRAY;
    LIME;
    MAGENTA;
    MARRON;
    NAVY;
    OLIVE;
    ORANGE;
    PINK;
    PURPLE;
    RED;
    RYOAL;
    SALMON;
    SCARLET;
    SKY;
    SLATE;
    TAN;
    TEAL;
    VIOLET;
    WHITE;
    YELLOW;
} declare var Color: Color;

// --- //

interface ActualArrayList extends jV {
    add(Object: "key");
    remove(Object: "key");
    contains(key: "Object"): boolean;
    indexOf(key: "Object"): "number";
    subList(from: "number", to: "number"): ActualArrayList;
    size(): "number";
    clear();
}
interface ArrayList {
    create(key: Object): ActualArrayList;
} declare var ArrayList: ArrayList;


// --- //
interface ColorAttribute {
    createDiffuse(Color): ColorAttribute
    createDiffuse(... float): ColorAttribute;
    createAmbient(Color): ColorAttribute;
    createAmbient(... float): ColorAttribute;
    createReflection(Color): ColorAttribute;
    createReflection(... float): ColorAttribute;
    createSpecular(Color);
    createSpecular(... float);
} declare var ColorAttribute: ColorAttribute;


// --- //

interface Editor {
    open(): Editor;
}
interface Map {
    Editor: Editor;
} declare var Map: Map;

// --- //

interface Duration_ extends jV {
    toHours(): number;
    toMillis(): number;
    toMinutes(): number;
    toSeconds(): number;
}
interface Duration {
    seconds(number): Duration_;
    millis(number): Duration_;
    hours(number): Duration_;
    minutes(number): Duration_;
} declare var Duration: Duration;
interface MediaPlayer_Status extends jV { }
interface MediaPlayer {
    stop(): void;
    play(): void;
    setVolume(number): void;
    getVolume(): number;
    setStartTime(Duration_): void;
    setStopTime(Duration_): void;
    getDuration(): Duration_;
    getBalance(): number;
    getStatus(): MediaPlayer_Status;
}
interface Sounds {
    mediaPlayer: MediaPlayer;
}
interface Sound {
    newSound(File: String): Sounds;
    stopAllSounds(): void;
} declare var Sound: Sound;

// --- //
interface Dialog_Option{} // placeholder so you can't put an int.
interface Dialog {
    YES_NO_CANCEL_OPTION: Dialog_Option;
    YES_NO_OPTION: Dialog_Option;
    YES_OPTION: Dialog_Option;
    CLOSED_OPTION: Dialog_Option;
    NO_OPTION: Dialog_Option;
    OK_OPTION: Dialog_Option;
    CANCEL_OPTION: Dialog_Option;
    OK_CANCEL_OPTION: Dialog_Option;
    //PLAIN_MESSAGE: number;
    //ERROR_MESSAGE: number;
    //QUESTION_MESSAGE: number;
    //WARNING_MESSAGE: number;
    showConfirmDialog(Title: String, Message: String, Dialog_Option): number;
    showInputDialog(Title: String, Message: String, Dialog_Option): String;
} declare var Dialog: Dialog;

interface Class<T> {

}
interface int extends jV {
    //byteValue(): byte;
    floatValue(): float;
}
interface Byte {

} declare var byte: Byte;
interface long extends jV {

} declare var long: number;
interface float extends jV {
    compareTo(float: float): number;
    longValue(): long;
    isNaN(): boolean;
    isInfinite(): boolean;
    intValue(): number;
    byteValue(): Byte;
} declare var float: number;
interface FloatArray<T> {
    toFloatArray(): number[];
    isEmpty(): boolean;
    add(number: number): boolean;
    remove(number: number): boolean;
    get(number: number): float;
    clear(): void;
    indexOf(number: number): number;
}