export enum STORE_KEY {
    AUTH_TOKEN = "token"
}
export const setValueToStore = (key: STORE_KEY, value: string) => {
    localStorage.setItem(key, value)
}

export const getValueFromStore = (key: STORE_KEY) => {
    return localStorage.getItem(key)
}