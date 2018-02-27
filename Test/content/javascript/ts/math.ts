
module math {
    export interface Matrix4 {
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
        translate(x: number, y: number, z: number): Matrix4;
        translate(vector3: Vector3): Matrix4;
    }
    export interface Vector3 {
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
    /*export interface int extends jV {
        //byteValue(): byte;
        floatValue(): float;
    }
    export interface Byte {

    }
    interface long extends jV {

    }
    interface Float extends jV {
        compareTo(float: float): number;
        longValue(): long;
        isNaN(): boolean;
        isInfinite(): boolean;
        intValue(): number;
        byteValue(): Byte;
    }
    interface FloatArray<T> {
        toFloatArray(): number[];
        isEmpty(): boolean;
        add(number: number): boolean;
        remove(number: number): boolean;
        get(number: number): Float;
        clear(): void;
        indexOf(number: number): number;
    }*/
    
}