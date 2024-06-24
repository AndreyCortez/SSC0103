import javax.swing.SwingUtilities;

// Andrey Cortez Rufino - 11819487
// Francyélio de Jesus Campos Lima - 13676537

// Classe main, serve apenas para inicializar a interface gráfica, mas poderia
// servir outros propósitos caso o código continuasse a ser desenvolvido
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Inicializa a interface de usuário
                new PlayerSearchApp().setVisible(true);
            }
        });
    }
}
