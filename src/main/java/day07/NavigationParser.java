package day07;

import java.util.List;

public class NavigationParser {
    private final Directory root;
    private Directory currentDir;

    private void cd(String name) {
        currentDir.addChild(name);
        currentDir = currentDir.getChild(name);
    }

    private void dir(String name) {
        currentDir.addChild(name);
    }

    private void file(String name, int size) {
        currentDir.addFile(name, size);
    }

    private void up() {
        currentDir = currentDir.getParent();
    }

    public NavigationParser(List<List<String>> commands) {
        root = new Directory("/");
        currentDir = root;
        boolean isLS = false;
        for (int i = 0; i < commands.size(); i++) {
            var curr = commands.get(i);
            if (curr.get(0).equalsIgnoreCase("$")) {
                if (curr.get(1).equalsIgnoreCase("cd")) {
                    isLS = false;
                    if (curr.get(2).equalsIgnoreCase("..")) {
                        up();
                    } else if (curr.get(2).equalsIgnoreCase("/")) {
                        currentDir = root;
                    } else {
                        cd(curr.get(2));
                    }
                } else if(curr.get(1).equalsIgnoreCase("ls")) {
                    isLS = true;
                }
            } else if (curr.get(0).equalsIgnoreCase("dir") && isLS) {
                dir(curr.get(1));
            } else if (isLS) {
                int size = Integer.valueOf(curr.get(0));
                String name = curr.get(1);
                file(name, size);
            }
        }
    }

    public Directory getRoot() {
        return root;
    }
}
