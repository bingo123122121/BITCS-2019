// Generated from CSubsetGrammarDesign.g4 by ANTLR 4.10.1
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
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#translationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranslationUnit(CSubsetGrammarDesignParser.TranslationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#externalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExternalDeclaration(CSubsetGrammarDesignParser.ExternalDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(CSubsetGrammarDesignParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#declarationList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationList(CSubsetGrammarDesignParser.DeclarationListContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(CSubsetGrammarDesignParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(CSubsetGrammarDesignParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(CSubsetGrammarDesignParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#selectionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectionStatement(CSubsetGrammarDesignParser.SelectionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#iterationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterationStatement(CSubsetGrammarDesignParser.IterationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStatement(CSubsetGrammarDesignParser.JumpStatementContext ctx);
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
	/**
	 * Visit a parse tree produced by {@link CSubsetGrammarDesignParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(CSubsetGrammarDesignParser.DeclarationContext ctx);
}