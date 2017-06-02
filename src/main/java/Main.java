import okhttp3.*;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {

    static List<XY> xyList = new ArrayList<XY>();
    static List<XYSize> xySizes = new ArrayList<XYSize>();
    static final int minZOOM = 7;
    public static void main(String[] args) {
        prepareData();
        loadTiles();
    }


    private static void prepareData(){
        xyList.add(new XY(82,38));
        xyList.add(new XY(165,77));
        xyList.add(new XY(331,155));
        xyList.add(new XY(662,310));
        xyList.add(new XY(1324,620));
        xyList.add(new XY(2648,1241));
        xyList.add(new XY(5297,2483));
        xyList.add(new XY(10595,4967));
        xyList.add(new XY(21191,9934));
//        xyList.add(new XY(42382,19867));
//        xyList.add(new XY(84765,39735));
//        xyList.add(new XY(169530,79471));

        xySizes.add(new XYSize(2,2));
        xySizes.add(new XYSize(2,2));
        xySizes.add(new XYSize(2,2));
        xySizes.add(new XYSize(3,4));
        xySizes.add(new XYSize(5,7));
        xySizes.add(new XYSize(10,12));
        xySizes.add(new XYSize(18,22));
        xySizes.add(new XYSize(35,43));
        xySizes.add(new XYSize(68,85));
//        xySizes.add(new XYSize(136,171));
//        xySizes.add(new XYSize(270,342));
//        xySizes.add(new XYSize(540,682));
    }

    private static void loadTiles(){
        int z = minZOOM;
        for (int i = 0; i < xyList.size(); i++){
            for(int xi = 0; xi<= xySizes.get(i).getSizeX() - 1; xi++){
                for (int xy = 0; xy<= xySizes.get(i).getSizeY() - 1; xy++){
                    try {
                        if (getImage(xyList.get(i).getX() + xi,xyList.get(i).getY() + xy, z)){
                            System.out.println(".....Done");
                        } else {
                            System.out.println(".....Fail!");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            ++z;
        }
    }


    private static boolean getImage(int x, int y, int z) throws IOException {
        String url = "https://sat0" + randomHostNum() + ".maps.yandex.net/tiles?l=sat&v=3.322.0&x=" + x + "&y=" + y + "&z=" + z + "&lang=ru_RU";
        System.out.print("Downloading file: " + url);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body() == null){
            return false;
        } else {
            File file = new File("c:/bingtiles/" + z + "/" + x + "/" + y + ".png");
            FileUtils.copyInputStreamToFile(response.body().byteStream(), file);
            return true;
        }
    }

    private static int randomHostNum(){
        return new Random().nextInt(4) + 1;
    }

    private static class XY {
        public XY(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    private static class XYSize {
        public XYSize(int sizeX, int sizeY) {
            this.sizeX = sizeX;
            this.sizeY = sizeY;
        }

        private int sizeX;
        private int sizeY;

        public int getSizeX() {
            return sizeX;
        }

        public void setSizeX(int sizeX) {
            this.sizeX = sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public void setSizeY(int sizeY) {
            this.sizeY = sizeY;
        }
    }




//    private static void executeLoading(String start, int maxZoomLevel){
//        int currentZoomLevel = start.length();
//        if (currentZoomLevel == maxZoomLevel){
//            System.out.println("MaxZoomLevel must be more");
//            return;
//        }
//        for (int i = currentZoomLevel; i < maxZoomLevel; i++){
//            loadRecursively(start, maxZoomLevel);
//        }
//    }
//
//    private static void loadRecursively(String start, int maxLevel){
//        for (int k=0; k < 4; k++) {
//            StringBuilder builder = new StringBuilder(start);
//            builder.append(String.valueOf(k));
//            loadTiles(builder.toString());
//            if (builder.toString().length() > maxLevel){
//                break;
//            }
//            loadRecursively(builder.toString(), maxLevel);
//        }
//    }
//
//    private static void loadTiles(String imageName){
//        String url = createUrl(imageName);
//        System.out.print("Downloading file: " + url + "......");
//        boolean reuslt = false;
//        try {
//            reuslt = getImage(url, imageName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (reuslt){
//            System.out.println("Done");
//        } else {
//            System.out.println("Fail");
//        }
//    }


//    private static String createUrl(String imageName){
//        StringBuilder builder = new StringBuilder();
//        builder.append("http://a0.ortho.tiles.virtualearth.net/tiles/a");
//        builder.append(imageName);
//        builder.append(".jpeg?g=72");
//        return builder.toString();
//    }


}
