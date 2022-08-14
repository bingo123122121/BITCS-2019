// Generated from CSubsetGrammarDesign.g4 by ANTLR 4.10.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CSubsetGrammarDesignParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, WS=38, TypeSpecifier=39, 
		MAIN=40, WHILE=41, FOR=42, IF=43, ELSE=44, CONTINUE=45, BREAK=46, RETURN=47, 
		Identifier=48, Constant=49, StringLiteral=50, Character=51, Digit=52, 
		NonDigit=53, NonzeroDigit=54, OctalDigit=55, NonzeroOctalDigit=56, HexadecimalDigit=57, 
		NonzeroHexadecimalDigit=58, IntegerSuffix=59, UnsignedInteger=60, DecimalIntegerConstant=61, 
		UnsignedOctalInteger=62, OctalIntegerConstant=63, UnsignedHexadecimalInteger=64, 
		HexadecimalIntegerConstant=65, IntegerConstant=66, FloatSuffix=67, DecimalFloatConstant=68, 
		HexadecimalFloatConstant=69, FloatConstant=70, Char=71, EscapeChar=72, 
		OctalEscapeChar=73, HexadecimalEscapeChar=74, CharConstant=75;
	public static final int
		RULE_translationUnit = 0, RULE_externalDeclaration = 1, RULE_functionDefinition = 2, 
		RULE_declarationList = 3, RULE_block = 4, RULE_statement = 5, RULE_expression = 6, 
		RULE_selectionStatement = 7, RULE_iterationStatement = 8, RULE_jumpStatement = 9, 
		RULE_assignmentExpression = 10, RULE_conditionalExpression = 11, RULE_logicalOrExpression = 12, 
		RULE_logicalAndExpression = 13, RULE_inclusiveOrExpression = 14, RULE_exclusiveOrExpression = 15, 
		RULE_andExpression = 16, RULE_equalityExpression = 17, RULE_relationalExpression = 18, 
		RULE_shiftExpression = 19, RULE_additiveExpression = 20, RULE_multiplicativeExpression = 21, 
		RULE_primaryExpression = 22, RULE_assignmentOperator = 23, RULE_declaration = 24;
	private static String[] makeRuleNames() {
		return new String[] {
			"translationUnit", "externalDeclaration", "functionDefinition", "declarationList", 
			"block", "statement", "expression", "selectionStatement", "iterationStatement", 
			"jumpStatement", "assignmentExpression", "conditionalExpression", "logicalOrExpression", 
			"logicalAndExpression", "inclusiveOrExpression", "exclusiveOrExpression", 
			"andExpression", "equalityExpression", "relationalExpression", "shiftExpression", 
			"additiveExpression", "multiplicativeExpression", "primaryExpression", 
			"assignmentOperator", "declaration"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'('", "')'", "'{'", "'}'", "','", "'?'", "':'", "'||'", 
			"'&&'", "'|'", "'^'", "'&'", "'=='", "'!='", "'<'", "'>'", "'<='", "'>='", 
			"'<<'", "'>>'", "'+'", "'-'", "'*'", "'/'", "'%'", "'='", "'*='", "'/='", 
			"'%='", "'+='", "'-='", "'<<='", "'>>='", "'&='", "'^='", "'|='", null, 
			null, "'main'", "'while'", "'for'", "'if'", "'else'", "'continue'", "'break'", 
			"'return'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "WS", "TypeSpecifier", "MAIN", "WHILE", "FOR", "IF", "ELSE", 
			"CONTINUE", "BREAK", "RETURN", "Identifier", "Constant", "StringLiteral", 
			"Character", "Digit", "NonDigit", "NonzeroDigit", "OctalDigit", "NonzeroOctalDigit", 
			"HexadecimalDigit", "NonzeroHexadecimalDigit", "IntegerSuffix", "UnsignedInteger", 
			"DecimalIntegerConstant", "UnsignedOctalInteger", "OctalIntegerConstant", 
			"UnsignedHexadecimalInteger", "HexadecimalIntegerConstant", "IntegerConstant", 
			"FloatSuffix", "DecimalFloatConstant", "HexadecimalFloatConstant", "FloatConstant", 
			"Char", "EscapeChar", "OctalEscapeChar", "HexadecimalEscapeChar", "CharConstant"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CSubsetGrammarDesign.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CSubsetGrammarDesignParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class TranslationUnitContext extends ParserRuleContext {
		public ExternalDeclarationContext externalDeclaration() {
			return getRuleContext(ExternalDeclarationContext.class,0);
		}
		public TranslationUnitContext translationUnit() {
			return getRuleContext(TranslationUnitContext.class,0);
		}
		public TranslationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_translationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterTranslationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitTranslationUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitTranslationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TranslationUnitContext translationUnit() throws RecognitionException {
		return translationUnit(0);
	}

	private TranslationUnitContext translationUnit(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TranslationUnitContext _localctx = new TranslationUnitContext(_ctx, _parentState);
		TranslationUnitContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_translationUnit, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(51);
			externalDeclaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(57);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TranslationUnitContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_translationUnit);
					setState(53);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(54);
					externalDeclaration();
					}
					} 
				}
				setState(59);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExternalDeclarationContext extends ParserRuleContext {
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExternalDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_externalDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterExternalDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitExternalDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitExternalDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExternalDeclarationContext externalDeclaration() throws RecognitionException {
		ExternalDeclarationContext _localctx = new ExternalDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_externalDeclaration);
		try {
			setState(65);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				functionDefinition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(61);
				declaration();
				setState(62);
				expression(0);
				setState(63);
				match(T__0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(CSubsetGrammarDesignParser.Identifier, 0); }
		public TerminalNode MAIN() { return getToken(CSubsetGrammarDesignParser.MAIN, 0); }
		public DeclarationListContext declarationList() {
			return getRuleContext(DeclarationListContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitFunctionDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			declaration();
			setState(68);
			_la = _input.LA(1);
			if ( !(_la==MAIN || _la==Identifier) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(69);
			match(T__1);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TypeSpecifier) {
				{
				setState(70);
				declarationList(0);
				}
			}

			setState(73);
			match(T__2);
			setState(74);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationListContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public DeclarationListContext declarationList() {
			return getRuleContext(DeclarationListContext.class,0);
		}
		public DeclarationListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarationList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterDeclarationList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitDeclarationList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitDeclarationList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationListContext declarationList() throws RecognitionException {
		return declarationList(0);
	}

	private DeclarationListContext declarationList(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		DeclarationListContext _localctx = new DeclarationListContext(_ctx, _parentState);
		DeclarationListContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_declarationList, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(77);
			declaration();
			}
			_ctx.stop = _input.LT(-1);
			setState(83);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new DeclarationListContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_declarationList);
					setState(79);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(80);
					declaration();
					}
					} 
				}
				setState(85);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_block);
		int _la;
		try {
			setState(97);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case TypeSpecifier:
			case WHILE:
			case FOR:
			case IF:
			case CONTINUE:
			case BREAK:
			case RETURN:
			case Identifier:
			case Constant:
			case StringLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(86);
				statement();
				setState(88);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(87);
					block();
					}
					break;
				}
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(90);
				match(T__3);
				setState(91);
				statement();
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__3) | (1L << TypeSpecifier) | (1L << WHILE) | (1L << FOR) | (1L << IF) | (1L << CONTINUE) | (1L << BREAK) | (1L << RETURN) | (1L << Identifier) | (1L << Constant) | (1L << StringLiteral))) != 0)) {
					{
					setState(92);
					block();
					}
				}

				setState(95);
				match(T__4);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public SelectionStatementContext selectionStatement() {
			return getRuleContext(SelectionStatementContext.class,0);
		}
		public IterationStatementContext iterationStatement() {
			return getRuleContext(IterationStatementContext.class,0);
		}
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_statement);
		int _la;
		try {
			setState(110);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case TypeSpecifier:
			case Identifier:
			case Constant:
			case StringLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TypeSpecifier) {
					{
					setState(99);
					declaration();
					}
				}

				setState(102);
				expression(0);
				setState(103);
				match(T__0);
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				selectionStatement();
				}
				break;
			case WHILE:
			case FOR:
				enterOuterAlt(_localctx, 3);
				{
				setState(106);
				iterationStatement();
				}
				break;
			case CONTINUE:
			case BREAK:
			case RETURN:
				enterOuterAlt(_localctx, 4);
				{
				setState(107);
				jumpStatement();
				setState(108);
				match(T__0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(113);
			assignmentExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(120);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_expression);
					setState(115);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(116);
					match(T__5);
					setState(117);
					assignmentExpression();
					}
					} 
				}
				setState(122);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class SelectionStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(CSubsetGrammarDesignParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(CSubsetGrammarDesignParser.ELSE, 0); }
		public SelectionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterSelectionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitSelectionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitSelectionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectionStatementContext selectionStatement() throws RecognitionException {
		SelectionStatementContext _localctx = new SelectionStatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_selectionStatement);
		try {
			setState(137);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(123);
				match(IF);
				setState(124);
				match(T__1);
				setState(125);
				expression(0);
				setState(126);
				match(T__2);
				setState(127);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(129);
				match(IF);
				setState(130);
				match(T__1);
				setState(131);
				expression(0);
				setState(132);
				match(T__2);
				setState(133);
				block();
				setState(134);
				match(ELSE);
				setState(135);
				block();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IterationStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(CSubsetGrammarDesignParser.WHILE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode FOR() { return getToken(CSubsetGrammarDesignParser.FOR, 0); }
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public IterationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iterationStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterIterationStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitIterationStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitIterationStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IterationStatementContext iterationStatement() throws RecognitionException {
		IterationStatementContext _localctx = new IterationStatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_iterationStatement);
		int _la;
		try {
			setState(176);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(139);
				match(WHILE);
				setState(140);
				match(T__1);
				setState(141);
				expression(0);
				setState(142);
				match(T__2);
				setState(143);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(145);
				match(FOR);
				setState(146);
				match(T__1);
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << TypeSpecifier) | (1L << Identifier) | (1L << Constant) | (1L << StringLiteral))) != 0)) {
					{
					setState(148);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==TypeSpecifier) {
						{
						setState(147);
						declaration();
						}
					}

					setState(150);
					expression(0);
					}
				}

				setState(153);
				match(T__0);
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << Identifier) | (1L << Constant) | (1L << StringLiteral))) != 0)) {
					{
					setState(154);
					expression(0);
					}
				}

				setState(157);
				match(T__0);
				setState(159);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << Identifier) | (1L << Constant) | (1L << StringLiteral))) != 0)) {
					{
					setState(158);
					expression(0);
					}
				}

				setState(161);
				match(T__2);
				setState(162);
				block();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(163);
				match(FOR);
				setState(164);
				match(T__1);
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TypeSpecifier) {
					{
					setState(165);
					declaration();
					setState(166);
					expression(0);
					}
				}

				setState(170);
				match(T__0);
				setState(172);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << Identifier) | (1L << Constant) | (1L << StringLiteral))) != 0)) {
					{
					setState(171);
					expression(0);
					}
				}

				setState(174);
				match(T__2);
				setState(175);
				block();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpStatementContext extends ParserRuleContext {
		public TerminalNode CONTINUE() { return getToken(CSubsetGrammarDesignParser.CONTINUE, 0); }
		public TerminalNode BREAK() { return getToken(CSubsetGrammarDesignParser.BREAK, 0); }
		public TerminalNode RETURN() { return getToken(CSubsetGrammarDesignParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterJumpStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitJumpStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitJumpStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_jumpStatement);
		int _la;
		try {
			setState(184);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONTINUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(178);
				match(CONTINUE);
				}
				break;
			case BREAK:
				enterOuterAlt(_localctx, 2);
				{
				setState(179);
				match(BREAK);
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 3);
				{
				setState(180);
				match(RETURN);
				setState(182);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << Identifier) | (1L << Constant) | (1L << StringLiteral))) != 0)) {
					{
					setState(181);
					expression(0);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentExpressionContext extends ParserRuleContext {
		public ConditionalExpressionContext conditionalExpression() {
			return getRuleContext(ConditionalExpressionContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(CSubsetGrammarDesignParser.Identifier, 0); }
		public AssignmentOperatorContext assignmentOperator() {
			return getRuleContext(AssignmentOperatorContext.class,0);
		}
		public AssignmentExpressionContext assignmentExpression() {
			return getRuleContext(AssignmentExpressionContext.class,0);
		}
		public AssignmentExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterAssignmentExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitAssignmentExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitAssignmentExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentExpressionContext assignmentExpression() throws RecognitionException {
		AssignmentExpressionContext _localctx = new AssignmentExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assignmentExpression);
		try {
			setState(191);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(186);
				conditionalExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(187);
				match(Identifier);
				setState(188);
				assignmentOperator();
				setState(189);
				assignmentExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionalExpressionContext extends ParserRuleContext {
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConditionalExpressionContext conditionalExpression() {
			return getRuleContext(ConditionalExpressionContext.class,0);
		}
		public ConditionalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditionalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterConditionalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitConditionalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitConditionalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalExpressionContext conditionalExpression() throws RecognitionException {
		ConditionalExpressionContext _localctx = new ConditionalExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_conditionalExpression);
		try {
			setState(200);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(193);
				logicalOrExpression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(194);
				logicalOrExpression(0);
				setState(195);
				match(T__6);
				setState(196);
				expression(0);
				setState(197);
				match(T__7);
				setState(198);
				conditionalExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalOrExpressionContext extends ParserRuleContext {
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public LogicalOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterLogicalOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitLogicalOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitLogicalOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOrExpressionContext logicalOrExpression() throws RecognitionException {
		return logicalOrExpression(0);
	}

	private LogicalOrExpressionContext logicalOrExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalOrExpressionContext _localctx = new LogicalOrExpressionContext(_ctx, _parentState);
		LogicalOrExpressionContext _prevctx = _localctx;
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_logicalOrExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(203);
			logicalAndExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(210);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LogicalOrExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_logicalOrExpression);
					setState(205);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(206);
					match(T__8);
					setState(207);
					logicalAndExpression(0);
					}
					} 
				}
				setState(212);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LogicalAndExpressionContext extends ParserRuleContext {
		public InclusiveOrExpressionContext inclusiveOrExpression() {
			return getRuleContext(InclusiveOrExpressionContext.class,0);
		}
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public LogicalAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterLogicalAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitLogicalAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitLogicalAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalAndExpressionContext logicalAndExpression() throws RecognitionException {
		return logicalAndExpression(0);
	}

	private LogicalAndExpressionContext logicalAndExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalAndExpressionContext _localctx = new LogicalAndExpressionContext(_ctx, _parentState);
		LogicalAndExpressionContext _prevctx = _localctx;
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_logicalAndExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(214);
			inclusiveOrExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(221);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new LogicalAndExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_logicalAndExpression);
					setState(216);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(217);
					match(T__9);
					setState(218);
					inclusiveOrExpression(0);
					}
					} 
				}
				setState(223);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class InclusiveOrExpressionContext extends ParserRuleContext {
		public ExclusiveOrExpressionContext exclusiveOrExpression() {
			return getRuleContext(ExclusiveOrExpressionContext.class,0);
		}
		public InclusiveOrExpressionContext inclusiveOrExpression() {
			return getRuleContext(InclusiveOrExpressionContext.class,0);
		}
		public InclusiveOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inclusiveOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterInclusiveOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitInclusiveOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitInclusiveOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InclusiveOrExpressionContext inclusiveOrExpression() throws RecognitionException {
		return inclusiveOrExpression(0);
	}

	private InclusiveOrExpressionContext inclusiveOrExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		InclusiveOrExpressionContext _localctx = new InclusiveOrExpressionContext(_ctx, _parentState);
		InclusiveOrExpressionContext _prevctx = _localctx;
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_inclusiveOrExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(225);
			exclusiveOrExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(232);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new InclusiveOrExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_inclusiveOrExpression);
					setState(227);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(228);
					match(T__10);
					setState(229);
					exclusiveOrExpression(0);
					}
					} 
				}
				setState(234);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExclusiveOrExpressionContext extends ParserRuleContext {
		public AndExpressionContext andExpression() {
			return getRuleContext(AndExpressionContext.class,0);
		}
		public ExclusiveOrExpressionContext exclusiveOrExpression() {
			return getRuleContext(ExclusiveOrExpressionContext.class,0);
		}
		public ExclusiveOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exclusiveOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterExclusiveOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitExclusiveOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitExclusiveOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExclusiveOrExpressionContext exclusiveOrExpression() throws RecognitionException {
		return exclusiveOrExpression(0);
	}

	private ExclusiveOrExpressionContext exclusiveOrExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExclusiveOrExpressionContext _localctx = new ExclusiveOrExpressionContext(_ctx, _parentState);
		ExclusiveOrExpressionContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_exclusiveOrExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(236);
			andExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(243);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExclusiveOrExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_exclusiveOrExpression);
					setState(238);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(239);
					match(T__11);
					setState(240);
					andExpression(0);
					}
					} 
				}
				setState(245);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AndExpressionContext extends ParserRuleContext {
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public AndExpressionContext andExpression() {
			return getRuleContext(AndExpressionContext.class,0);
		}
		public AndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndExpressionContext andExpression() throws RecognitionException {
		return andExpression(0);
	}

	private AndExpressionContext andExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AndExpressionContext _localctx = new AndExpressionContext(_ctx, _parentState);
		AndExpressionContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_andExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(247);
			equalityExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(254);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AndExpressionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_andExpression);
					setState(249);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(250);
					match(T__12);
					setState(251);
					equalityExpression(0);
					}
					} 
				}
				setState(256);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class EqualityExpressionContext extends ParserRuleContext {
		public RelationalExpressionContext relationalExpression() {
			return getRuleContext(RelationalExpressionContext.class,0);
		}
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public EqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterEqualityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitEqualityExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExpressionContext equalityExpression() throws RecognitionException {
		return equalityExpression(0);
	}

	private EqualityExpressionContext equalityExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		EqualityExpressionContext _localctx = new EqualityExpressionContext(_ctx, _parentState);
		EqualityExpressionContext _prevctx = _localctx;
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_equalityExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(258);
			relationalExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(268);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(266);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
					case 1:
						{
						_localctx = new EqualityExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(260);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(261);
						match(T__13);
						setState(262);
						relationalExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new EqualityExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(263);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(264);
						match(T__14);
						setState(265);
						relationalExpression(0);
						}
						break;
					}
					} 
				}
				setState(270);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class RelationalExpressionContext extends ParserRuleContext {
		public ShiftExpressionContext shiftExpression() {
			return getRuleContext(ShiftExpressionContext.class,0);
		}
		public RelationalExpressionContext relationalExpression() {
			return getRuleContext(RelationalExpressionContext.class,0);
		}
		public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterRelationalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitRelationalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitRelationalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalExpressionContext relationalExpression() throws RecognitionException {
		return relationalExpression(0);
	}

	private RelationalExpressionContext relationalExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RelationalExpressionContext _localctx = new RelationalExpressionContext(_ctx, _parentState);
		RelationalExpressionContext _prevctx = _localctx;
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_relationalExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(272);
			shiftExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(288);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(286);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
					case 1:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(274);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(275);
						match(T__15);
						setState(276);
						shiftExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(277);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(278);
						match(T__16);
						setState(279);
						shiftExpression(0);
						}
						break;
					case 3:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(280);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(281);
						match(T__17);
						setState(282);
						shiftExpression(0);
						}
						break;
					case 4:
						{
						_localctx = new RelationalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_relationalExpression);
						setState(283);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(284);
						match(T__18);
						setState(285);
						shiftExpression(0);
						}
						break;
					}
					} 
				}
				setState(290);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ShiftExpressionContext extends ParserRuleContext {
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public ShiftExpressionContext shiftExpression() {
			return getRuleContext(ShiftExpressionContext.class,0);
		}
		public ShiftExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shiftExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterShiftExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitShiftExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitShiftExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShiftExpressionContext shiftExpression() throws RecognitionException {
		return shiftExpression(0);
	}

	private ShiftExpressionContext shiftExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ShiftExpressionContext _localctx = new ShiftExpressionContext(_ctx, _parentState);
		ShiftExpressionContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_shiftExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(292);
			additiveExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(302);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(300);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
					case 1:
						{
						_localctx = new ShiftExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_shiftExpression);
						setState(294);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(295);
						match(T__19);
						setState(296);
						additiveExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new ShiftExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_shiftExpression);
						setState(297);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(298);
						match(T__20);
						setState(299);
						additiveExpression(0);
						}
						break;
					}
					} 
				}
				setState(304);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AdditiveExpressionContext extends ParserRuleContext {
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public AdditiveExpressionContext additiveExpression() {
			return getRuleContext(AdditiveExpressionContext.class,0);
		}
		public AdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterAdditiveExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitAdditiveExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitAdditiveExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExpressionContext additiveExpression() throws RecognitionException {
		return additiveExpression(0);
	}

	private AdditiveExpressionContext additiveExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AdditiveExpressionContext _localctx = new AdditiveExpressionContext(_ctx, _parentState);
		AdditiveExpressionContext _prevctx = _localctx;
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_additiveExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(306);
			multiplicativeExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(316);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(314);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
					case 1:
						{
						_localctx = new AdditiveExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_additiveExpression);
						setState(308);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(309);
						match(T__21);
						setState(310);
						multiplicativeExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new AdditiveExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_additiveExpression);
						setState(311);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(312);
						match(T__22);
						setState(313);
						multiplicativeExpression(0);
						}
						break;
					}
					} 
				}
				setState(318);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class MultiplicativeExpressionContext extends ParserRuleContext {
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public MultiplicativeExpressionContext multiplicativeExpression() {
			return getRuleContext(MultiplicativeExpressionContext.class,0);
		}
		public MultiplicativeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterMultiplicativeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitMultiplicativeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitMultiplicativeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExpressionContext multiplicativeExpression() throws RecognitionException {
		return multiplicativeExpression(0);
	}

	private MultiplicativeExpressionContext multiplicativeExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MultiplicativeExpressionContext _localctx = new MultiplicativeExpressionContext(_ctx, _parentState);
		MultiplicativeExpressionContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_multiplicativeExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(320);
			primaryExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(333);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(331);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicativeExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(322);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(323);
						match(T__23);
						setState(324);
						primaryExpression();
						}
						break;
					case 2:
						{
						_localctx = new MultiplicativeExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(325);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(326);
						match(T__24);
						setState(327);
						primaryExpression();
						}
						break;
					case 3:
						{
						_localctx = new MultiplicativeExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicativeExpression);
						setState(328);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(329);
						match(T__25);
						setState(330);
						primaryExpression();
						}
						break;
					}
					} 
				}
				setState(335);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PrimaryExpressionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(CSubsetGrammarDesignParser.Identifier, 0); }
		public TerminalNode Constant() { return getToken(CSubsetGrammarDesignParser.Constant, 0); }
		public TerminalNode StringLiteral() { return getToken(CSubsetGrammarDesignParser.StringLiteral, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrimaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitPrimaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitPrimaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryExpressionContext primaryExpression() throws RecognitionException {
		PrimaryExpressionContext _localctx = new PrimaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_primaryExpression);
		try {
			setState(343);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(336);
				match(Identifier);
				}
				break;
			case Constant:
				enterOuterAlt(_localctx, 2);
				{
				setState(337);
				match(Constant);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 3);
				{
				setState(338);
				match(StringLiteral);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 4);
				{
				setState(339);
				match(T__1);
				setState(340);
				expression(0);
				setState(341);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentOperatorContext extends ParserRuleContext {
		public AssignmentOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterAssignmentOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitAssignmentOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitAssignmentOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentOperatorContext assignmentOperator() throws RecognitionException {
		AssignmentOperatorContext _localctx = new AssignmentOperatorContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_assignmentOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(345);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public TerminalNode TypeSpecifier() { return getToken(CSubsetGrammarDesignParser.TypeSpecifier, 0); }
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CSubsetGrammarDesignListener ) ((CSubsetGrammarDesignListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CSubsetGrammarDesignVisitor ) return ((CSubsetGrammarDesignVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(347);
			match(TypeSpecifier);
			setState(349);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(348);
				declaration();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return translationUnit_sempred((TranslationUnitContext)_localctx, predIndex);
		case 3:
			return declarationList_sempred((DeclarationListContext)_localctx, predIndex);
		case 6:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 12:
			return logicalOrExpression_sempred((LogicalOrExpressionContext)_localctx, predIndex);
		case 13:
			return logicalAndExpression_sempred((LogicalAndExpressionContext)_localctx, predIndex);
		case 14:
			return inclusiveOrExpression_sempred((InclusiveOrExpressionContext)_localctx, predIndex);
		case 15:
			return exclusiveOrExpression_sempred((ExclusiveOrExpressionContext)_localctx, predIndex);
		case 16:
			return andExpression_sempred((AndExpressionContext)_localctx, predIndex);
		case 17:
			return equalityExpression_sempred((EqualityExpressionContext)_localctx, predIndex);
		case 18:
			return relationalExpression_sempred((RelationalExpressionContext)_localctx, predIndex);
		case 19:
			return shiftExpression_sempred((ShiftExpressionContext)_localctx, predIndex);
		case 20:
			return additiveExpression_sempred((AdditiveExpressionContext)_localctx, predIndex);
		case 21:
			return multiplicativeExpression_sempred((MultiplicativeExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean translationUnit_sempred(TranslationUnitContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean declarationList_sempred(DeclarationListContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean logicalOrExpression_sempred(LogicalOrExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean logicalAndExpression_sempred(LogicalAndExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean inclusiveOrExpression_sempred(InclusiveOrExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean exclusiveOrExpression_sempred(ExclusiveOrExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean andExpression_sempred(AndExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean equalityExpression_sempred(EqualityExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 2);
		case 9:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean relationalExpression_sempred(RelationalExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 4);
		case 11:
			return precpred(_ctx, 3);
		case 12:
			return precpred(_ctx, 2);
		case 13:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean shiftExpression_sempred(ShiftExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 14:
			return precpred(_ctx, 2);
		case 15:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean additiveExpression_sempred(AdditiveExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 16:
			return precpred(_ctx, 2);
		case 17:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean multiplicativeExpression_sempred(MultiplicativeExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 18:
			return precpred(_ctx, 3);
		case 19:
			return precpred(_ctx, 2);
		case 20:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001K\u0160\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000"+
		"8\b\u0000\n\u0000\f\u0000;\t\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0003\u0001B\b\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0003\u0002H\b\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0005\u0003R\b\u0003\n\u0003\f\u0003U\t\u0003\u0001\u0004\u0001\u0004"+
		"\u0003\u0004Y\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"^\b\u0004\u0001\u0004\u0001\u0004\u0003\u0004b\b\u0004\u0001\u0005\u0003"+
		"\u0005e\b\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005o\b\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005"+
		"\u0006w\b\u0006\n\u0006\f\u0006z\t\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003"+
		"\u0007\u008a\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b\u0095\b\b\u0001\b\u0003\b\u0098\b\b\u0001\b"+
		"\u0001\b\u0003\b\u009c\b\b\u0001\b\u0001\b\u0003\b\u00a0\b\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u00a9\b\b\u0001\b\u0001"+
		"\b\u0003\b\u00ad\b\b\u0001\b\u0001\b\u0003\b\u00b1\b\b\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0003\t\u00b7\b\t\u0003\t\u00b9\b\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0003\n\u00c0\b\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00c9\b\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0005\f\u00d1\b\f\n\f"+
		"\f\f\u00d4\t\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r"+
		"\u00dc\b\r\n\r\f\r\u00df\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u00e7\b\u000e\n\u000e\f\u000e"+
		"\u00ea\t\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0005\u000f\u00f2\b\u000f\n\u000f\f\u000f\u00f5\t\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005"+
		"\u0010\u00fd\b\u0010\n\u0010\f\u0010\u0100\t\u0010\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0005\u0011\u010b\b\u0011\n\u0011\f\u0011\u010e\t\u0011\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0005\u0012\u011f\b\u0012\n\u0012\f\u0012"+
		"\u0122\t\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u012d\b\u0013"+
		"\n\u0013\f\u0013\u0130\t\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005"+
		"\u0014\u013b\b\u0014\n\u0014\f\u0014\u013e\t\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u014c\b\u0015"+
		"\n\u0015\f\u0015\u014f\t\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u0158\b\u0016\u0001"+
		"\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0003\u0018\u015e\b\u0018\u0001"+
		"\u0018\u0000\r\u0000\u0006\f\u0018\u001a\u001c\u001e \"$&(*\u0019\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c"+
		"\u001e \"$&(*,.0\u0000\u0002\u0002\u0000((00\u0001\u0000\u001b%\u0176"+
		"\u00002\u0001\u0000\u0000\u0000\u0002A\u0001\u0000\u0000\u0000\u0004C"+
		"\u0001\u0000\u0000\u0000\u0006L\u0001\u0000\u0000\u0000\ba\u0001\u0000"+
		"\u0000\u0000\nn\u0001\u0000\u0000\u0000\fp\u0001\u0000\u0000\u0000\u000e"+
		"\u0089\u0001\u0000\u0000\u0000\u0010\u00b0\u0001\u0000\u0000\u0000\u0012"+
		"\u00b8\u0001\u0000\u0000\u0000\u0014\u00bf\u0001\u0000\u0000\u0000\u0016"+
		"\u00c8\u0001\u0000\u0000\u0000\u0018\u00ca\u0001\u0000\u0000\u0000\u001a"+
		"\u00d5\u0001\u0000\u0000\u0000\u001c\u00e0\u0001\u0000\u0000\u0000\u001e"+
		"\u00eb\u0001\u0000\u0000\u0000 \u00f6\u0001\u0000\u0000\u0000\"\u0101"+
		"\u0001\u0000\u0000\u0000$\u010f\u0001\u0000\u0000\u0000&\u0123\u0001\u0000"+
		"\u0000\u0000(\u0131\u0001\u0000\u0000\u0000*\u013f\u0001\u0000\u0000\u0000"+
		",\u0157\u0001\u0000\u0000\u0000.\u0159\u0001\u0000\u0000\u00000\u015b"+
		"\u0001\u0000\u0000\u000023\u0006\u0000\uffff\uffff\u000034\u0003\u0002"+
		"\u0001\u000049\u0001\u0000\u0000\u000056\n\u0001\u0000\u000068\u0003\u0002"+
		"\u0001\u000075\u0001\u0000\u0000\u00008;\u0001\u0000\u0000\u000097\u0001"+
		"\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:\u0001\u0001\u0000\u0000"+
		"\u0000;9\u0001\u0000\u0000\u0000<B\u0003\u0004\u0002\u0000=>\u00030\u0018"+
		"\u0000>?\u0003\f\u0006\u0000?@\u0005\u0001\u0000\u0000@B\u0001\u0000\u0000"+
		"\u0000A<\u0001\u0000\u0000\u0000A=\u0001\u0000\u0000\u0000B\u0003\u0001"+
		"\u0000\u0000\u0000CD\u00030\u0018\u0000DE\u0007\u0000\u0000\u0000EG\u0005"+
		"\u0002\u0000\u0000FH\u0003\u0006\u0003\u0000GF\u0001\u0000\u0000\u0000"+
		"GH\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000IJ\u0005\u0003\u0000"+
		"\u0000JK\u0003\b\u0004\u0000K\u0005\u0001\u0000\u0000\u0000LM\u0006\u0003"+
		"\uffff\uffff\u0000MN\u00030\u0018\u0000NS\u0001\u0000\u0000\u0000OP\n"+
		"\u0001\u0000\u0000PR\u00030\u0018\u0000QO\u0001\u0000\u0000\u0000RU\u0001"+
		"\u0000\u0000\u0000SQ\u0001\u0000\u0000\u0000ST\u0001\u0000\u0000\u0000"+
		"T\u0007\u0001\u0000\u0000\u0000US\u0001\u0000\u0000\u0000VX\u0003\n\u0005"+
		"\u0000WY\u0003\b\u0004\u0000XW\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000"+
		"\u0000Yb\u0001\u0000\u0000\u0000Z[\u0005\u0004\u0000\u0000[]\u0003\n\u0005"+
		"\u0000\\^\u0003\b\u0004\u0000]\\\u0001\u0000\u0000\u0000]^\u0001\u0000"+
		"\u0000\u0000^_\u0001\u0000\u0000\u0000_`\u0005\u0005\u0000\u0000`b\u0001"+
		"\u0000\u0000\u0000aV\u0001\u0000\u0000\u0000aZ\u0001\u0000\u0000\u0000"+
		"b\t\u0001\u0000\u0000\u0000ce\u00030\u0018\u0000dc\u0001\u0000\u0000\u0000"+
		"de\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fg\u0003\f\u0006\u0000"+
		"gh\u0005\u0001\u0000\u0000ho\u0001\u0000\u0000\u0000io\u0003\u000e\u0007"+
		"\u0000jo\u0003\u0010\b\u0000kl\u0003\u0012\t\u0000lm\u0005\u0001\u0000"+
		"\u0000mo\u0001\u0000\u0000\u0000nd\u0001\u0000\u0000\u0000ni\u0001\u0000"+
		"\u0000\u0000nj\u0001\u0000\u0000\u0000nk\u0001\u0000\u0000\u0000o\u000b"+
		"\u0001\u0000\u0000\u0000pq\u0006\u0006\uffff\uffff\u0000qr\u0003\u0014"+
		"\n\u0000rx\u0001\u0000\u0000\u0000st\n\u0001\u0000\u0000tu\u0005\u0006"+
		"\u0000\u0000uw\u0003\u0014\n\u0000vs\u0001\u0000\u0000\u0000wz\u0001\u0000"+
		"\u0000\u0000xv\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000\u0000y\r\u0001"+
		"\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000{|\u0005+\u0000\u0000|}\u0005"+
		"\u0002\u0000\u0000}~\u0003\f\u0006\u0000~\u007f\u0005\u0003\u0000\u0000"+
		"\u007f\u0080\u0003\b\u0004\u0000\u0080\u008a\u0001\u0000\u0000\u0000\u0081"+
		"\u0082\u0005+\u0000\u0000\u0082\u0083\u0005\u0002\u0000\u0000\u0083\u0084"+
		"\u0003\f\u0006\u0000\u0084\u0085\u0005\u0003\u0000\u0000\u0085\u0086\u0003"+
		"\b\u0004\u0000\u0086\u0087\u0005,\u0000\u0000\u0087\u0088\u0003\b\u0004"+
		"\u0000\u0088\u008a\u0001\u0000\u0000\u0000\u0089{\u0001\u0000\u0000\u0000"+
		"\u0089\u0081\u0001\u0000\u0000\u0000\u008a\u000f\u0001\u0000\u0000\u0000"+
		"\u008b\u008c\u0005)\u0000\u0000\u008c\u008d\u0005\u0002\u0000\u0000\u008d"+
		"\u008e\u0003\f\u0006\u0000\u008e\u008f\u0005\u0003\u0000\u0000\u008f\u0090"+
		"\u0003\b\u0004\u0000\u0090\u00b1\u0001\u0000\u0000\u0000\u0091\u0092\u0005"+
		"*\u0000\u0000\u0092\u0097\u0005\u0002\u0000\u0000\u0093\u0095\u00030\u0018"+
		"\u0000\u0094\u0093\u0001\u0000\u0000\u0000\u0094\u0095\u0001\u0000\u0000"+
		"\u0000\u0095\u0096\u0001\u0000\u0000\u0000\u0096\u0098\u0003\f\u0006\u0000"+
		"\u0097\u0094\u0001\u0000\u0000\u0000\u0097\u0098\u0001\u0000\u0000\u0000"+
		"\u0098\u0099\u0001\u0000\u0000\u0000\u0099\u009b\u0005\u0001\u0000\u0000"+
		"\u009a\u009c\u0003\f\u0006\u0000\u009b\u009a\u0001\u0000\u0000\u0000\u009b"+
		"\u009c\u0001\u0000\u0000\u0000\u009c\u009d\u0001\u0000\u0000\u0000\u009d"+
		"\u009f\u0005\u0001\u0000\u0000\u009e\u00a0\u0003\f\u0006\u0000\u009f\u009e"+
		"\u0001\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0\u00a1"+
		"\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005\u0003\u0000\u0000\u00a2\u00b1"+
		"\u0003\b\u0004\u0000\u00a3\u00a4\u0005*\u0000\u0000\u00a4\u00a8\u0005"+
		"\u0002\u0000\u0000\u00a5\u00a6\u00030\u0018\u0000\u00a6\u00a7\u0003\f"+
		"\u0006\u0000\u00a7\u00a9\u0001\u0000\u0000\u0000\u00a8\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000"+
		"\u0000\u0000\u00aa\u00ac\u0005\u0001\u0000\u0000\u00ab\u00ad\u0003\f\u0006"+
		"\u0000\u00ac\u00ab\u0001\u0000\u0000\u0000\u00ac\u00ad\u0001\u0000\u0000"+
		"\u0000\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u00af\u0005\u0003\u0000"+
		"\u0000\u00af\u00b1\u0003\b\u0004\u0000\u00b0\u008b\u0001\u0000\u0000\u0000"+
		"\u00b0\u0091\u0001\u0000\u0000\u0000\u00b0\u00a3\u0001\u0000\u0000\u0000"+
		"\u00b1\u0011\u0001\u0000\u0000\u0000\u00b2\u00b9\u0005-\u0000\u0000\u00b3"+
		"\u00b9\u0005.\u0000\u0000\u00b4\u00b6\u0005/\u0000\u0000\u00b5\u00b7\u0003"+
		"\f\u0006\u0000\u00b6\u00b5\u0001\u0000\u0000\u0000\u00b6\u00b7\u0001\u0000"+
		"\u0000\u0000\u00b7\u00b9\u0001\u0000\u0000\u0000\u00b8\u00b2\u0001\u0000"+
		"\u0000\u0000\u00b8\u00b3\u0001\u0000\u0000\u0000\u00b8\u00b4\u0001\u0000"+
		"\u0000\u0000\u00b9\u0013\u0001\u0000\u0000\u0000\u00ba\u00c0\u0003\u0016"+
		"\u000b\u0000\u00bb\u00bc\u00050\u0000\u0000\u00bc\u00bd\u0003.\u0017\u0000"+
		"\u00bd\u00be\u0003\u0014\n\u0000\u00be\u00c0\u0001\u0000\u0000\u0000\u00bf"+
		"\u00ba\u0001\u0000\u0000\u0000\u00bf\u00bb\u0001\u0000\u0000\u0000\u00c0"+
		"\u0015\u0001\u0000\u0000\u0000\u00c1\u00c9\u0003\u0018\f\u0000\u00c2\u00c3"+
		"\u0003\u0018\f\u0000\u00c3\u00c4\u0005\u0007\u0000\u0000\u00c4\u00c5\u0003"+
		"\f\u0006\u0000\u00c5\u00c6\u0005\b\u0000\u0000\u00c6\u00c7\u0003\u0016"+
		"\u000b\u0000\u00c7\u00c9\u0001\u0000\u0000\u0000\u00c8\u00c1\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c2\u0001\u0000\u0000\u0000\u00c9\u0017\u0001\u0000"+
		"\u0000\u0000\u00ca\u00cb\u0006\f\uffff\uffff\u0000\u00cb\u00cc\u0003\u001a"+
		"\r\u0000\u00cc\u00d2\u0001\u0000\u0000\u0000\u00cd\u00ce\n\u0001\u0000"+
		"\u0000\u00ce\u00cf\u0005\t\u0000\u0000\u00cf\u00d1\u0003\u001a\r\u0000"+
		"\u00d0\u00cd\u0001\u0000\u0000\u0000\u00d1\u00d4\u0001\u0000\u0000\u0000"+
		"\u00d2\u00d0\u0001\u0000\u0000\u0000\u00d2\u00d3\u0001\u0000\u0000\u0000"+
		"\u00d3\u0019\u0001\u0000\u0000\u0000\u00d4\u00d2\u0001\u0000\u0000\u0000"+
		"\u00d5\u00d6\u0006\r\uffff\uffff\u0000\u00d6\u00d7\u0003\u001c\u000e\u0000"+
		"\u00d7\u00dd\u0001\u0000\u0000\u0000\u00d8\u00d9\n\u0001\u0000\u0000\u00d9"+
		"\u00da\u0005\n\u0000\u0000\u00da\u00dc\u0003\u001c\u000e\u0000\u00db\u00d8"+
		"\u0001\u0000\u0000\u0000\u00dc\u00df\u0001\u0000\u0000\u0000\u00dd\u00db"+
		"\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000\u0000\u00de\u001b"+
		"\u0001\u0000\u0000\u0000\u00df\u00dd\u0001\u0000\u0000\u0000\u00e0\u00e1"+
		"\u0006\u000e\uffff\uffff\u0000\u00e1\u00e2\u0003\u001e\u000f\u0000\u00e2"+
		"\u00e8\u0001\u0000\u0000\u0000\u00e3\u00e4\n\u0001\u0000\u0000\u00e4\u00e5"+
		"\u0005\u000b\u0000\u0000\u00e5\u00e7\u0003\u001e\u000f\u0000\u00e6\u00e3"+
		"\u0001\u0000\u0000\u0000\u00e7\u00ea\u0001\u0000\u0000\u0000\u00e8\u00e6"+
		"\u0001\u0000\u0000\u0000\u00e8\u00e9\u0001\u0000\u0000\u0000\u00e9\u001d"+
		"\u0001\u0000\u0000\u0000\u00ea\u00e8\u0001\u0000\u0000\u0000\u00eb\u00ec"+
		"\u0006\u000f\uffff\uffff\u0000\u00ec\u00ed\u0003 \u0010\u0000\u00ed\u00f3"+
		"\u0001\u0000\u0000\u0000\u00ee\u00ef\n\u0001\u0000\u0000\u00ef\u00f0\u0005"+
		"\f\u0000\u0000\u00f0\u00f2\u0003 \u0010\u0000\u00f1\u00ee\u0001\u0000"+
		"\u0000\u0000\u00f2\u00f5\u0001\u0000\u0000\u0000\u00f3\u00f1\u0001\u0000"+
		"\u0000\u0000\u00f3\u00f4\u0001\u0000\u0000\u0000\u00f4\u001f\u0001\u0000"+
		"\u0000\u0000\u00f5\u00f3\u0001\u0000\u0000\u0000\u00f6\u00f7\u0006\u0010"+
		"\uffff\uffff\u0000\u00f7\u00f8\u0003\"\u0011\u0000\u00f8\u00fe\u0001\u0000"+
		"\u0000\u0000\u00f9\u00fa\n\u0001\u0000\u0000\u00fa\u00fb\u0005\r\u0000"+
		"\u0000\u00fb\u00fd\u0003\"\u0011\u0000\u00fc\u00f9\u0001\u0000\u0000\u0000"+
		"\u00fd\u0100\u0001\u0000\u0000\u0000\u00fe\u00fc\u0001\u0000\u0000\u0000"+
		"\u00fe\u00ff\u0001\u0000\u0000\u0000\u00ff!\u0001\u0000\u0000\u0000\u0100"+
		"\u00fe\u0001\u0000\u0000\u0000\u0101\u0102\u0006\u0011\uffff\uffff\u0000"+
		"\u0102\u0103\u0003$\u0012\u0000\u0103\u010c\u0001\u0000\u0000\u0000\u0104"+
		"\u0105\n\u0002\u0000\u0000\u0105\u0106\u0005\u000e\u0000\u0000\u0106\u010b"+
		"\u0003$\u0012\u0000\u0107\u0108\n\u0001\u0000\u0000\u0108\u0109\u0005"+
		"\u000f\u0000\u0000\u0109\u010b\u0003$\u0012\u0000\u010a\u0104\u0001\u0000"+
		"\u0000\u0000\u010a\u0107\u0001\u0000\u0000\u0000\u010b\u010e\u0001\u0000"+
		"\u0000\u0000\u010c\u010a\u0001\u0000\u0000\u0000\u010c\u010d\u0001\u0000"+
		"\u0000\u0000\u010d#\u0001\u0000\u0000\u0000\u010e\u010c\u0001\u0000\u0000"+
		"\u0000\u010f\u0110\u0006\u0012\uffff\uffff\u0000\u0110\u0111\u0003&\u0013"+
		"\u0000\u0111\u0120\u0001\u0000\u0000\u0000\u0112\u0113\n\u0004\u0000\u0000"+
		"\u0113\u0114\u0005\u0010\u0000\u0000\u0114\u011f\u0003&\u0013\u0000\u0115"+
		"\u0116\n\u0003\u0000\u0000\u0116\u0117\u0005\u0011\u0000\u0000\u0117\u011f"+
		"\u0003&\u0013\u0000\u0118\u0119\n\u0002\u0000\u0000\u0119\u011a\u0005"+
		"\u0012\u0000\u0000\u011a\u011f\u0003&\u0013\u0000\u011b\u011c\n\u0001"+
		"\u0000\u0000\u011c\u011d\u0005\u0013\u0000\u0000\u011d\u011f\u0003&\u0013"+
		"\u0000\u011e\u0112\u0001\u0000\u0000\u0000\u011e\u0115\u0001\u0000\u0000"+
		"\u0000\u011e\u0118\u0001\u0000\u0000\u0000\u011e\u011b\u0001\u0000\u0000"+
		"\u0000\u011f\u0122\u0001\u0000\u0000\u0000\u0120\u011e\u0001\u0000\u0000"+
		"\u0000\u0120\u0121\u0001\u0000\u0000\u0000\u0121%\u0001\u0000\u0000\u0000"+
		"\u0122\u0120\u0001\u0000\u0000\u0000\u0123\u0124\u0006\u0013\uffff\uffff"+
		"\u0000\u0124\u0125\u0003(\u0014\u0000\u0125\u012e\u0001\u0000\u0000\u0000"+
		"\u0126\u0127\n\u0002\u0000\u0000\u0127\u0128\u0005\u0014\u0000\u0000\u0128"+
		"\u012d\u0003(\u0014\u0000\u0129\u012a\n\u0001\u0000\u0000\u012a\u012b"+
		"\u0005\u0015\u0000\u0000\u012b\u012d\u0003(\u0014\u0000\u012c\u0126\u0001"+
		"\u0000\u0000\u0000\u012c\u0129\u0001\u0000\u0000\u0000\u012d\u0130\u0001"+
		"\u0000\u0000\u0000\u012e\u012c\u0001\u0000\u0000\u0000\u012e\u012f\u0001"+
		"\u0000\u0000\u0000\u012f\'\u0001\u0000\u0000\u0000\u0130\u012e\u0001\u0000"+
		"\u0000\u0000\u0131\u0132\u0006\u0014\uffff\uffff\u0000\u0132\u0133\u0003"+
		"*\u0015\u0000\u0133\u013c\u0001\u0000\u0000\u0000\u0134\u0135\n\u0002"+
		"\u0000\u0000\u0135\u0136\u0005\u0016\u0000\u0000\u0136\u013b\u0003*\u0015"+
		"\u0000\u0137\u0138\n\u0001\u0000\u0000\u0138\u0139\u0005\u0017\u0000\u0000"+
		"\u0139\u013b\u0003*\u0015\u0000\u013a\u0134\u0001\u0000\u0000\u0000\u013a"+
		"\u0137\u0001\u0000\u0000\u0000\u013b\u013e\u0001\u0000\u0000\u0000\u013c"+
		"\u013a\u0001\u0000\u0000\u0000\u013c\u013d\u0001\u0000\u0000\u0000\u013d"+
		")\u0001\u0000\u0000\u0000\u013e\u013c\u0001\u0000\u0000\u0000\u013f\u0140"+
		"\u0006\u0015\uffff\uffff\u0000\u0140\u0141\u0003,\u0016\u0000\u0141\u014d"+
		"\u0001\u0000\u0000\u0000\u0142\u0143\n\u0003\u0000\u0000\u0143\u0144\u0005"+
		"\u0018\u0000\u0000\u0144\u014c\u0003,\u0016\u0000\u0145\u0146\n\u0002"+
		"\u0000\u0000\u0146\u0147\u0005\u0019\u0000\u0000\u0147\u014c\u0003,\u0016"+
		"\u0000\u0148\u0149\n\u0001\u0000\u0000\u0149\u014a\u0005\u001a\u0000\u0000"+
		"\u014a\u014c\u0003,\u0016\u0000\u014b\u0142\u0001\u0000\u0000\u0000\u014b"+
		"\u0145\u0001\u0000\u0000\u0000\u014b\u0148\u0001\u0000\u0000\u0000\u014c"+
		"\u014f\u0001\u0000\u0000\u0000\u014d\u014b\u0001\u0000\u0000\u0000\u014d"+
		"\u014e\u0001\u0000\u0000\u0000\u014e+\u0001\u0000\u0000\u0000\u014f\u014d"+
		"\u0001\u0000\u0000\u0000\u0150\u0158\u00050\u0000\u0000\u0151\u0158\u0005"+
		"1\u0000\u0000\u0152\u0158\u00052\u0000\u0000\u0153\u0154\u0005\u0002\u0000"+
		"\u0000\u0154\u0155\u0003\f\u0006\u0000\u0155\u0156\u0005\u0003\u0000\u0000"+
		"\u0156\u0158\u0001\u0000\u0000\u0000\u0157\u0150\u0001\u0000\u0000\u0000"+
		"\u0157\u0151\u0001\u0000\u0000\u0000\u0157\u0152\u0001\u0000\u0000\u0000"+
		"\u0157\u0153\u0001\u0000\u0000\u0000\u0158-\u0001\u0000\u0000\u0000\u0159"+
		"\u015a\u0007\u0001\u0000\u0000\u015a/\u0001\u0000\u0000\u0000\u015b\u015d"+
		"\u0005\'\u0000\u0000\u015c\u015e\u00030\u0018\u0000\u015d\u015c\u0001"+
		"\u0000\u0000\u0000\u015d\u015e\u0001\u0000\u0000\u0000\u015e1\u0001\u0000"+
		"\u0000\u0000\'9AGSX]adnx\u0089\u0094\u0097\u009b\u009f\u00a8\u00ac\u00b0"+
		"\u00b6\u00b8\u00bf\u00c8\u00d2\u00dd\u00e8\u00f3\u00fe\u010a\u010c\u011e"+
		"\u0120\u012c\u012e\u013a\u013c\u014b\u014d\u0157\u015d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}