package Server;

import java.util.ArrayList;

//Clase que tiene una lista con las películas disponibles
public class Cinema {

    private final ArrayList<Movie> movies; 

    public Cinema() throws CinemaException {
        movies = new ArrayList<>();
        fill(); //se llena la lista de películas con los datos de prueba
    }

    //devuelve una hilera con la lista de películas disponibles
    public String getMoviesString() {
        String text = "Movie List:\n";
        for (int i = 0; i < movies.size(); i++) {
            text += (i + 1) + ") " + movies.get(i).getDescription() + "\n";
        }
        return text;
    }
    
    //Retorna una película, recibe el número de pelicula seleccionada
    public Movie getMovie(int index) {
        return movies.get(index);
    }
    
    //datos de prueba 
    private void fill() throws CinemaException {
        movies.add(new Movie("Deadpool 2, 06-06-20, 5:00 pm", 5, 5)); 
        movies.add(new Movie("Deadpool 2, 06-06-20, 8:00 pm", 5, 5)); 
        movies.add(new Movie("StarWars, 06-06-20, 7:00 pm", 4, 5));
        movies.add(new Movie("StarWars, 06-06-20, 9:00 pm", 4, 5));
        movies.add(new Movie("Vengadores: Infinity War, 06-06-20, 3:00 pm", 3, 4));
    }
}
