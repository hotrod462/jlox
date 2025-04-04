package com.craftinginterpreters.lox;

public class AstPrinter implements Expr.Visitor<String>{
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr){
        return paranthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return paranthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr){
        if(expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr){
        return paranthesize(expr.operator.lexeme, expr.right);
    }

    public String visitTernaryExpr(Expr.Ternary expr) {
        return paranthesize( expr.operator1.lexeme + expr.operator2.lexeme, expr.condition, expr.thenBranch, expr.elseBranch);
    }
    public String visitVariableExpr(Expr.Variable expr) {
        if(expr.name == null) return "nil";
        return expr.name.lexeme;
    }
    private String paranthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for(Expr expr: exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));

        }
        builder.append(")");

        return builder.toString();
    }


    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
        new Expr.Unary(
        new Token(TokenType.MINUS, "-", null, 1),
        new Expr.Literal(123)),
        new Token(TokenType.STAR, "*", null, 1),
        new Expr.Grouping(
        new Expr.Literal(45.67)));

        System.out.println(new AstPrinter().print(expression));
        }
}
