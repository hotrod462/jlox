package com.craftinginterpreters.lox;

public class PostFix implements Expr.Visitor<String>{
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return postorder(expr.operator.lexeme, expr.left, expr.right);
    }

    public String visitGroupingExpr(Expr.Grouping expr) {
        return postorder("", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if(expr.value == null) return "nil";
        return expr.value.toString();   
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return postorder(expr.operator.lexeme  ,new Expr.Literal(0) , expr.right    
        );
    }

    private String postorder(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        for(Expr expr: exprs){
            builder.append(" ");
            builder.append(expr.accept(this));
            //builder.append(" ");
        }
        builder.append(" ");
        builder.append(name);

        return builder.toString();
    }
    
    // Temporary main method to test the PostFix functionality with different types of expressions
    public static void main(String[] args) {
        PostFix printer = new PostFix();

        // Test Literal expression
        Expr literal = new Expr.Literal(42);
        System.out.println("Literal: " + printer.print(literal));

        // Test Grouping expression
        Expr grouping = new Expr.Grouping(new Expr.Literal(3.14));
        System.out.println("Grouping: " + printer.print(grouping));

        // Test Unary expression
        Expr unary = new Expr.Unary(new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(99));
        System.out.println("Unary: " + printer.print(unary));

        // Test Binary expression
        Expr binary = new Expr.Binary(new Expr.Literal(7), new Token(TokenType.PLUS, "+", null, 1), new Expr.Literal(5));
        System.out.println("Binary: " + printer.print(binary));

        // Test Complex expression combining different types
        Expr complex = new Expr.Binary(
            new Expr.Unary(new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(8)),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(new Expr.Literal(2))
        );
        System.out.println("Complex: " + printer.print(complex));
    }
}
