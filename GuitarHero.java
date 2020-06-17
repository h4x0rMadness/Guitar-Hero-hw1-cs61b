import es.datastructur.synthesizer.GuitarString;

public class GuitarHero {
    GuitarString[] strings;
    public static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public GuitarHero() {
        strings = new GuitarString[37];
        //keyboard  = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

        for (int i = 0; i < strings.length; i++) {
            double upper = ((double) (i - 24)) / 12;
            double freq = 440 * Math.pow(2, upper);
            strings[i] = new GuitarString(freq);
        }
    }
    public static void main(String[] args) {
        GuitarHero gh = new GuitarHero();


        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int indexOfKey = keyboard.indexOf(key);
                if (indexOfKey != -1) {
                    gh.strings[indexOfKey].pluck();
                }

            }

            double sample = 0;
            for (GuitarString gs : gh.strings) {
                sample += gs.sample();
            }

            StdAudio.play(sample);

            for (GuitarString gs : gh.strings) {
                gs.tic();
            }


        }
    }
}
