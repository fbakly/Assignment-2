import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

public class Cacher {
    private String path;
    private HashSet<Long> IDs;

    public Cacher(String path) {
        this.path = path;
        this.IDs = new HashSet<>();
    }

    public HashSet<Long> readFile() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(this.path))) {
            var line = "";
            while ((line = br.readLine()) != null) {
                IDs.add(Long.parseLong(line));
            }
            br.close();
        } catch (Exception e) {
        }
        return IDs;
    }

    public String getLastArticle() {
        var returnString = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(this.path))) {
            var line = "";
            while ((line = br.readLine()) != null) {
                returnString.append(line);
            }
            br.close();
        } catch (Exception e) {
        }
        return returnString.toString();
    }

    public void writeFile(HashSet<Long> IDs) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(this.path))) {
            var sb = new StringBuilder();
            IDs.stream().forEach(i -> {
                sb.append(i + "\n");
            });
            bw.write(sb.toString());
            bw.flush();
            bw.close();
        } catch (Exception e) {

        }
    }

    public void writeFile(String toWrite) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(this.path))) {
            bw.write(toWrite.toString());
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
