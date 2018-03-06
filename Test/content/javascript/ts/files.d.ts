declare module files {
    export interface uri extends jV {
        getAuthority(): string;
        getFragment(): string;
        getHost(): string;
        getPath(): string;
        getPort(): number;
        getQuery(): string;
        getRawAuthority(): string;
        getRawFragment(): string;
        getRawHost(): string;
        getRawPath(): string;
        getRawQuery(): string;
        getRawSchemeSpecificPart(): string;
        getRawUserInfo(): string;
        getScheme(): string;
        getSchemeSpecificPart(): string;
        getUserInfo(): string;
        isAbsolute(): boolean;
        isOpaque(): string;
        normalize(): uri;
        parseServerAuthority(): uri;
        relativize(uri: uri): uri;
        resolve(uri: uri): uri;
        resolve(str: string): uri;
        toASCIIString(): string;
    }
    export interface fileClass extends jV {
        exists(): boolean;
        getPath(): string;
        isFile(): boolean;
        mkdir(): boolean;
        getName(): string;
        delete(): boolean;
        isDirectory(): boolean;
        length(): number;
        deleteOnExit(): void;
        getAbsolutePath(): string;
        listFiles(): fileClass[];
        list(): string[];
        toURI(): uri;
        canExecute(): boolean;
        canRead(): boolean;
        canWrite(): boolean;
        compareTo(similar: fileClass): number;
        createNewFile(): boolean;
        //getCanonicalFile(): fileClass;
        getFreeSpace(): number;
        getParent(): string;
        getParentFile(): fileClass;
        getTotalSpace(): number;
        getUseableSpace(): number;
    }
    export interface File {
        new(file: string): fileClass;
    }
}