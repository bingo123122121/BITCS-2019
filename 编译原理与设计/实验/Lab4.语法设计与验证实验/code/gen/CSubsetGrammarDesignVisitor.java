// Generated from D:/大三下/编译原理与设计/实验/Lab4.语法设计与验证实验/code/src\CSubsetGrammarDesign.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CSubsetGrammarDesignParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CSubsetGrammarDesignVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(CSubsetGrammarDesignParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(CSubsetGrammarDesignParser.AssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#conditionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(CSubsetGrammarDesignParser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpression(CSubsetGrammarDesignParser.LogicalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpression(CSubsetGrammarDesignParser.LogicalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusiveOrExpression(CSubsetGrammarDesignParser.InclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusiveOrExpression(CSubsetGrammarDesignParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(CSubsetGrammarDesignParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(CSubsetGrammarDesignParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(CSubsetGrammarDesignParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#shiftExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpression(CSubsetGrammarDesignParser.ShiftExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(CSubsetGrammarDesignParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(CSubsetGrammarDesignParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpression(CSubsetGrammarDesignParser.PrimaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(CSubsetGrammarDesignParser.AssignmentOperatorContext ctx);
}