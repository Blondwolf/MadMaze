package madmaze.hearc.ch.madmaze.game.controller;

/**
 * Created by KVull on 10.11.2017.
 */

public class UpdateThread extends Thread {

        GameController controller;

        boolean keepUpdating;
        float delta;

        public UpdateThread(GameController controller){
            this.controller = controller;
            delta = 0;
            keepUpdating = false;
        }

        @Override
        public void run() {
            keepUpdating = true;
            while (keepUpdating) {
                controller.update(0);//TODO calculate delta time

                // Update at 50 fps
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {}
            }
        }

        public void stopThread(){
            keepUpdating = false;

            boolean joined = false;
            while (!joined) {
                try {
                    this.join();    //Maybe cannot be in the "this" class
                    joined = true;
                } catch (InterruptedException e) {}
            }
        }
}
