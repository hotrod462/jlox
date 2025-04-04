package com.craftinginterpreters.lox;

import java.util.List;

abstract class Expr {
 interface Visitor<R> {
    R visitBinaryExpr(Binary expr);
    R visitGroupingExpr(Grouping expr);
    R visitLiteralExpr(Literal expr);
    R visitUnaryExpr(Unary expr);
    R visitTernaryExpr(Ternary expr);
    R visitVariableExpr(Variable expr);
 }
 static class Binary extends Expr {
    Binary(Expr left, Token operator, Expr right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
    }

    @Override
    <R> R accept(Visitor <R> visitor) {
    return visitor.visitBinaryExpr(this);
    }

    final Expr left;
    final Token operator;
    final Expr right;
 }
 static class Grouping extends Expr {
    Grouping(Expr expression) {
    this.expression = expression;
    }

    @Override
    <R> R accept(Visitor <R> visitor) {
    return visitor.visitGroupingExpr(this);
    }

    final Expr expression;
 }
 static class Literal extends Expr {
    Literal(Object value) {
    this.value = value;
    }

    @Override
    <R> R accept(Visitor <R> visitor) {
    return visitor.visitLiteralExpr(this);
    }

    final Object value;
 }
 static class Unary extends Expr {
    Unary(Token operator, Expr right) {
    this.operator = operator;
    this.right = right;
    }

    @Override
    <R> R accept(Visitor <R> visitor) {
    return visitor.visitUnaryExpr(this);
    }

    final Token operator;
    final Expr right;
 }
 static class Ternary extends Expr {
    Ternary(Expr condition, Token operator1, Expr thenBranch, Token operator2, Expr elseBranch) {
    this.condition = condition;
    this.operator1 = operator1;
    this.thenBranch = thenBranch;
    this.operator2 = operator2;
    this.elseBranch = elseBranch;
    }

    @Override
    <R> R accept(Visitor <R> visitor) {
    return visitor.visitTernaryExpr(this);
    }

    final Expr condition;
    final Token operator1;
    final Expr thenBranch;
    final Token operator2;
    final Expr elseBranch;
 }
 static class Variable extends Expr {
    Variable(Token name) {
    this.name = name;
    }

    @Override
    <R> R accept(Visitor <R> visitor) {
    return visitor.visitVariableExpr(this);
    }

    final Token name;
 }

 abstract <R> R accept(Visitor<R> visitor);
}
