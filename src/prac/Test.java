package prac;

import java.util.List;
import java.util.Stack;

public class Test {

    static class TextEditor {
        private Document document;
        private CommandManager commandManager;
        private Cursor cursor;

        public void insert(String text) {

        }

        public void delete(int start, int end) {

        }

        public void applyStyle(int start, int end, Style style) {

        }

        public void undo() {

        }

        public void redo() {

        }
    }

    static class TextSegment {
        private String text;
        private Style style;
    }

    static class Document {
        private List<Line> lines;

        public void insert(int position, String text) {

        }

        public void delete(int start, int end) {

        }

        public void applyStyle(int start, int end, Style style) {

        }
    }

    static class Line {
        private List<TextSegment> segments;
    }

    static class Style {
        private boolean bold;
        private boolean italic;
        private boolean underline;
    }

    static class Cursor {
        private int position;
    }

    interface Command {
        void execute();

        void undo();
    }

    static class CommandManager {
        private Stack<Command> undoStack;
        private Stack<Command> redoStack;
    }

}
