package Server;

import java.util.ArrayList;

//Clase que tiene los datos de la película: la descripción y los asientos
//Los asientos son una matriz lista de booleanos, libres en true, ocupados en false
public class Movie {

    private String description;
    private ArrayList<ArrayList<Character>> seats;
    private static final char FREE = 'L';
    private static final char OCCUPIED = 'O';
    private static final char SELECTED = 'S';

    public Movie(String description, int rows, int columns) throws CinemaException {
        if (rows < 1 || columns < 1) {
            throw new CinemaException("Error en cantidad de asientos");
        }
        this.description = description;
        seats = new ArrayList<>();
        initializeSeats(rows, columns);  //llama a inicializar los asientos en true
    }

    public String getDescription() {
        return description;
    }

    //llena todos los asientos con true
    private void initializeSeats(int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            seats.add(new ArrayList<>());
            for (int j = 0; j < columns; j++) {
                seats.get(i).add(FREE);
            }
        }
    }

    /*Devulve una hilera con la distribución de los asientos
    cada asiento tiene su fila y columna, los asientos libres tienen una L, los ocupados una O y los seleccionados una S.
    Ejemplo de implresión
        0-0: O  0-1: L  0-2: L  
        1-0: L  1-1: O  1-2: L  
        2-0: L  2-1: S  2-2: S
    */   
    public String getSeatsString() {
        String txt = "";
        for (int row = 0; row < seats.size(); row++) {
            ArrayList<Character> rowList = seats.get(row);
            for (int column = 0; column < rowList.size(); column++) {
                txt += row + "-" + column + ": " + rowList.get(column) + "  ";
            }
            txt += "\n";
        }
        return txt;
    }

    //método para comprar un asiento, recibe la fila y la columna del asinto a comprar
    //valida que la fila y la columna recibidas sean valores válidos
    //también que el asiento en la fila y columma recibidas esté libre, osea en true
    //retorna si se pudo o no comprar el asiento
    public boolean setSeat(int row, int column) {
        if (row >= 0 && row < seats.size()
                && column >= 0 && column < seats.get(row).size()
                && seats.get(row).get(column) == FREE) {
            seats.get(row).set(column, SELECTED);
            return true;
        }
        return false;
    }


    //método que deshace la selección de asientos
    public void undoSeatsSelection() {
        for (int row = 0; row < seats.size(); row++) {
            ArrayList<Character> rowList = seats.get(row);
            for (int column = 0; column < rowList.size(); column++) {
                if (seats.get(row).get(column) == SELECTED) {
                    seats.get(row).set(column, FREE);            
                }                
            }            
        }
    }
    
    //método que establece la selección de asientos
    public void saveSeatsSelection() {
        for (int row = 0; row < seats.size(); row++) {
            ArrayList<Character> rowList = seats.get(row);
            for (int column = 0; column < rowList.size(); column++) {
                if (seats.get(row).get(column) == SELECTED) {
                    seats.get(row).set(column, OCCUPIED);            
                }                
            }
        }
    }
}
