import javax.swing.SwingUtilities;

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
