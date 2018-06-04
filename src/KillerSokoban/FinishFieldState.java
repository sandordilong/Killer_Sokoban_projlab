package KillerSokoban;

/**
 * Enumeráció. Tárolja, hogy a FinishField melyik állapotában van. A Free azt jelenti, hogy még soha nem volt rajta Box,
 * az Occupied azt, hogy van rajta Box és már nem ereszti el.
 */
public enum FinishFieldState {
    Free, Occupied
}
