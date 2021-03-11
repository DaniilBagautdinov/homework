package tasks.first;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

public class FirstTaskSolution implements FirstTask {
    @Override
    public String breadthFirst(boolean[][] adjacencyMatrix, int startIndex) {
        ArrayDeque<Vertex> vertexesInQueue = new ArrayDeque<>();
        ArrayList<Vertex> vertexArrayList = new ArrayList<>();
        int indexNow = startIndex;
        String result = "";

        while (vertexArrayList.size() != adjacencyMatrix.length) {
            if (checkDuplicateVertexes(vertexArrayList, new Vertex(indexNow))) {
                vertexesInQueue.offer(new Vertex(indexNow));
            }

            for (int i = 0; i < adjacencyMatrix.length; i++) {
                if (adjacencyMatrix[indexNow][i] &&
                        checkDuplicateVertexes(vertexesInQueue, new Vertex(i)) &&
                        checkDuplicateVertexes(vertexArrayList, new Vertex(i))) {
                    vertexesInQueue.offer(new Vertex(i));
                }
            }

            Vertex vertex = vertexesInQueue.pollFirst();
            vertexArrayList.add(vertex);
            indexNow = vertex.getIndex();


            if (vertexArrayList.size() == adjacencyMatrix.length) {
                result += vertex.getIndex();
            } else {
                result += vertex.getIndex() + ",";
            }
        }
        return result;
    }

    private boolean checkDuplicateVertexes(Collection<Vertex> vertexes, Vertex thisVertex) {
        for (Vertex vertex : vertexes) {
            if (vertex.getIndex() == thisVertex.getIndex()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean validateBrackets(String s) {
        ArrayDeque<Character> stack = new ArrayDeque<>();
        String openBrackets = "({[";
        String closeBrackets = ")}]";
        for (Character symbol : s.toCharArray()) {
            if (openBrackets.indexOf(symbol) != -1) {
                stack.push(symbol);
            } else if (closeBrackets.indexOf(symbol) != -1) {
                if (!stack.isEmpty()) {
                    Character elem = stack.pop();
                    if ((elem.equals('(') && symbol != ')') ||
                            (elem.equals('[') && symbol != ']') ||
                            (elem.equals('{') && symbol != '}')) {
                        return false;
                    }
                } else return false;
            }
        }
        return true;
    }

    @Override
    public Long polishCalculation(String s) {
        ArrayDeque<Long> stack = new ArrayDeque<>();
        String[] elements = s.split(" ");
        long number1, number2;
        for (String element : elements) {
            if (isThatANumber(element)) {
                stack.addFirst(Long.parseLong(element));
            } else {
                number1 = stack.pop();
                number2 = stack.pop();
                if(element.equals("+"))
                    stack.addFirst(number1 + number2);
                else if(element.equals("-"))
                    stack.addFirst(number2 - number1);
                else if(element.equals("*"))
                    stack.addFirst(number1 * number2);
                else if(element.equals("/"))
                    stack.addFirst(number2 / number1);
            }
        }
        if (!stack.isEmpty()) {
            return stack.pop();
        } else {
            return null;
        }
    }

    public boolean isThatANumber(String sth) {
        try {
            Long.parseLong(sth);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
