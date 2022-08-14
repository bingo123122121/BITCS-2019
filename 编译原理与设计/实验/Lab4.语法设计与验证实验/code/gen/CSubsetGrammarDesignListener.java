// Generated from D:/大三下/编译原理与设计/实验/Lab4.语法设计与验证实验/code/src\CSubsetGrammarDesign.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CSubsetGrammarDesignParser}.
 */
public interface CSubsetGrammarDesignListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CSubsetGrammarDesignParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CSubsetGrammarDesignParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(CSubsetGrammarDesignParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(CSubsetGrammarDesignParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(CSubsetGrammarDesignParser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#conditionalExpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(CSubsetGrammarDesignParser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(CSubsetGrammarDesignParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(CSubsetGrammarDesignParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(CSubsetGrammarDesignParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(CSubsetGrammarDesignParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterInclusiveOrExpression(CSubsetGrammarDesignParser.InclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitInclusiveOrExpression(CSubsetGrammarDesignParser.InclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveOrExpression(CSubsetGrammarDesignParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveOrExpression(CSubsetGrammarDesignParser.ExclusiveOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(CSubsetGrammarDesignParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(CSubsetGrammarDesignParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(CSubsetGrammarDesignParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(CSubsetGrammarDesignParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(CSubsetGrammarDesignParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(CSubsetGrammarDesignParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpression(CSubsetGrammarDesignParser.ShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#shiftExpression}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpression(CSubsetGrammarDesignParser.ShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(CSubsetGrammarDesignParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(CSubsetGrammarDesignParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(CSubsetGrammarDesignParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(CSubsetGrammarDesignParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(CSubsetGrammarDesignParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(CSubsetGrammarDesignParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(CSubsetGrammarDesignParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(CSubsetGrammarDesignParser.AssignmentOperatorContext ctx);
}