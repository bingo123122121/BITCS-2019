// Generated from CSubsetGrammarDesign.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CSubsetGrammarDesignParser}.
 */
public interface CSubsetGrammarDesignListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void enterTranslationUnit(CSubsetGrammarDesignParser.TranslationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void exitTranslationUnit(CSubsetGrammarDesignParser.TranslationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#externalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterExternalDeclaration(CSubsetGrammarDesignParser.ExternalDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#externalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitExternalDeclaration(CSubsetGrammarDesignParser.ExternalDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(CSubsetGrammarDesignParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(CSubsetGrammarDesignParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationList(CSubsetGrammarDesignParser.DeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#declarationList}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationList(CSubsetGrammarDesignParser.DeclarationListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(CSubsetGrammarDesignParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(CSubsetGrammarDesignParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CSubsetGrammarDesignParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CSubsetGrammarDesignParser.StatementContext ctx);
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
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectionStatement(CSubsetGrammarDesignParser.SelectionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectionStatement(CSubsetGrammarDesignParser.SelectionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterationStatement(CSubsetGrammarDesignParser.IterationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterationStatement(CSubsetGrammarDesignParser.IterationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpStatement(CSubsetGrammarDesignParser.JumpStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpStatement(CSubsetGrammarDesignParser.JumpStatementContext ctx);
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
	/**
	 * Enter a parse tree produced by {@link CSubsetGrammarDesignParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(CSubsetGrammarDesignParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSubsetGrammarDesignParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(CSubsetGrammarDesignParser.DeclarationContext ctx);
}