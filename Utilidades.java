public class Utilidades {

    public static boolean notaEntre0e10(float nota) {
        return (0.0f <= nota && nota <= 10.0f);
    }

    public static String classificarDesempenho(float nota) {
        if(nota < 5.0f) {
            return "Insuficiente";
        } else if(5.0f <= nota && nota < 7.0f) {
            return "Regular";
        } else {
            return "Adequado";
        }
    }

    public static float calcularPercentual(float a, float b) {
        if(b != 0.0f) {
            return a / b;
        } else {
            return 0;
        }
    }

}
