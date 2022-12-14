// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: myScanner.l

import java.io.*;


// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
class Scanner {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0, 0
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\u10ff\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\6\3\17\0\1\1\1\4\1\5"+
    "\1\6\1\0\1\4\1\7\1\10\2\6\1\11\1\12"+
    "\1\6\1\13\1\14\1\15\1\16\7\17\1\20\1\21"+
    "\2\22\1\23\1\24\1\25\1\26\1\0\4\27\1\30"+
    "\1\31\5\32\1\33\3\32\1\34\4\32\1\35\2\32"+
    "\1\36\2\32\1\6\1\37\1\6\1\4\1\32\1\0"+
    "\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47"+
    "\1\50\1\32\1\51\1\52\1\53\1\54\1\55\1\56"+
    "\1\32\1\57\1\60\1\61\1\62\1\63\1\64\1\65"+
    "\1\66\1\67\1\6\1\70\1\6\1\22\1\0\u0180\3";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[512];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\2\4\1\3\1\4\4\3"+
    "\3\5\3\3\21\6\1\3\10\0\2\5\1\0\2\7"+
    "\2\0\1\7\2\0\6\6\1\10\4\6\1\10\13\6"+
    "\1\11\1\0\1\12\3\0\1\13\2\7\4\0\2\7"+
    "\1\0\5\5\32\6\4\0\3\5\1\7\1\0\1\7"+
    "\1\0\1\7\1\0\1\7\21\6\1\0\5\7\1\0"+
    "\3\5\12\6";

  private static int [] zzUnpackAction() {
    int [] result = new int[175];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\71\0\162\0\253\0\344\0\162\0\u011d\0\u0156"+
    "\0\u018f\0\u01c8\0\u0201\0\u023a\0\u0273\0\u02ac\0\u02e5\0\162"+
    "\0\u031e\0\u0357\0\u0390\0\u03c9\0\u0402\0\u043b\0\u0474\0\u04ad"+
    "\0\u04e6\0\u051f\0\u0558\0\u0591\0\u05ca\0\u0603\0\u063c\0\u0675"+
    "\0\u06ae\0\u06e7\0\u0720\0\u0759\0\u0792\0\u07cb\0\u0804\0\u083d"+
    "\0\u0876\0\u08af\0\u08e8\0\u0921\0\u095a\0\u0993\0\u09cc\0\162"+
    "\0\u0a05\0\u0a3e\0\u0a77\0\u0ab0\0\344\0\u0156\0\u0ae9\0\u0b22"+
    "\0\u0b5b\0\u0b94\0\u0bcd\0\u0c06\0\u0c3f\0\u0c78\0\u0cb1\0\u0cea"+
    "\0\u0d23\0\u0390\0\u0d5c\0\u0d95\0\u0dce\0\u0e07\0\u0e40\0\u0e79"+
    "\0\u0eb2\0\u0eeb\0\u0f24\0\u0f5d\0\u0f96\0\u0792\0\u0fcf\0\162"+
    "\0\u1008\0\u1041\0\u107a\0\162\0\u10b3\0\u10ec\0\u1125\0\u115e"+
    "\0\u1197\0\u11d0\0\u1209\0\u1242\0\u127b\0\u12b4\0\u12ed\0\u1326"+
    "\0\u135f\0\u1398\0\u13d1\0\u140a\0\u1443\0\u147c\0\u14b5\0\u14ee"+
    "\0\u1527\0\u1560\0\u1599\0\u15d2\0\u160b\0\u1644\0\u167d\0\u16b6"+
    "\0\u16ef\0\u1728\0\u1761\0\u179a\0\u17d3\0\u180c\0\u1845\0\u187e"+
    "\0\u18b7\0\u18f0\0\u1929\0\u1962\0\u199b\0\u19d4\0\u1a0d\0\u1a46"+
    "\0\162\0\u1a7f\0\u1ab8\0\u1af1\0\u1b2a\0\u1b63\0\u1b9c\0\u1bd5"+
    "\0\u1c0e\0\u1c47\0\u1c80\0\u1cb9\0\u1cf2\0\u1d2b\0\u1d64\0\u1d9d"+
    "\0\u1dd6\0\u1e0f\0\u1e48\0\u1e81\0\u1eba\0\u1ef3\0\u1f2c\0\u1f65"+
    "\0\u1f9e\0\u1fd7\0\u2010\0\u2049\0\u2082\0\u20bb\0\u20f4\0\u212d"+
    "\0\u2166\0\u219f\0\u21d8\0\u2211\0\u224a\0\u2283\0\u22bc\0\u22f5"+
    "\0\u232e\0\u2367\0\u23a0\0\u23d9\0\u2412\0\u244b\0\u2484";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[175];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\0\1\2\1\3\1\0\1\4\1\5\1\6\1\7"+
    "\1\10\1\4\1\11\1\12\1\13\1\14\1\15\1\16"+
    "\1\17\1\16\1\20\1\21\1\4\1\22\1\20\4\23"+
    "\1\24\1\23\1\24\1\23\1\0\1\25\1\26\1\27"+
    "\1\30\1\31\1\32\1\33\1\23\1\34\1\23\1\35"+
    "\4\23\1\36\1\37\1\40\1\41\1\42\1\43\3\23"+
    "\1\44\1\0\1\2\204\0\1\20\44\0\2\45\2\0"+
    "\4\45\1\0\26\45\1\46\31\45\7\0\1\20\14\0"+
    "\1\20\44\0\2\47\2\0\4\47\1\0\26\47\1\50"+
    "\31\47\12\0\1\20\3\0\1\15\3\16\2\0\1\20"+
    "\57\0\1\20\2\0\1\15\3\16\2\0\2\20\57\0"+
    "\1\51\65\0\1\52\3\0\1\53\6\0\1\20\60\0"+
    "\1\54\1\0\1\55\1\56\10\0\1\57\1\60\1\0"+
    "\1\61\1\0\1\62\1\63\5\0\1\57\1\60\4\0"+
    "\1\64\7\0\1\62\2\0\1\63\17\0\1\54\1\0"+
    "\4\16\6\0\1\57\1\60\1\0\1\61\1\0\1\62"+
    "\6\0\1\57\1\60\4\0\1\64\7\0\1\62\13\0"+
    "\1\65\6\0\1\54\1\0\4\16\6\0\1\57\1\60"+
    "\1\0\1\61\1\0\1\62\6\0\1\57\1\60\4\0"+
    "\1\64\7\0\1\62\31\0\1\4\1\20\70\0\1\20"+
    "\1\4\61\0\4\23\5\0\10\23\1\0\30\23\6\0"+
    "\1\65\2\0\1\66\5\0\4\23\5\0\10\23\1\0"+
    "\30\23\17\0\4\23\5\0\10\23\1\0\22\23\1\67"+
    "\5\23\17\0\4\23\5\0\10\23\1\0\17\23\1\70"+
    "\10\23\17\0\4\23\5\0\10\23\1\0\1\71\6\23"+
    "\1\72\5\23\1\73\12\23\17\0\4\23\5\0\10\23"+
    "\1\0\4\23\1\74\10\23\1\75\12\23\17\0\4\23"+
    "\5\0\10\23\1\0\12\23\1\71\1\23\1\76\10\23"+
    "\1\77\2\23\17\0\4\23\5\0\10\23\1\0\12\23"+
    "\1\100\2\23\1\101\12\23\17\0\4\23\5\0\10\23"+
    "\1\0\15\23\1\67\12\23\17\0\4\23\5\0\10\23"+
    "\1\0\5\23\1\102\6\23\1\103\13\23\17\0\4\23"+
    "\5\0\10\23\1\0\15\23\1\104\12\23\17\0\4\23"+
    "\5\0\10\23\1\0\4\23\1\105\23\23\17\0\4\23"+
    "\5\0\10\23\1\0\7\23\1\106\1\107\10\23\1\110"+
    "\2\23\1\111\3\23\17\0\4\23\5\0\10\23\1\0"+
    "\26\23\1\112\1\23\6\0\1\65\2\0\1\66\5\0"+
    "\4\23\5\0\10\23\1\0\14\23\1\113\13\23\17\0"+
    "\4\23\5\0\10\23\1\0\15\23\1\114\12\23\17\0"+
    "\4\23\5\0\10\23\1\0\7\23\1\115\20\23\25\0"+
    "\1\20\43\0\1\20\2\45\2\0\1\45\1\116\2\45"+
    "\1\0\26\45\1\46\31\45\5\0\1\45\2\0\1\45"+
    "\5\0\2\45\6\0\1\45\7\0\1\117\3\45\3\0"+
    "\1\45\6\0\1\45\2\0\1\45\1\0\1\45\1\0"+
    "\1\45\1\0\1\117\13\0\1\120\65\0\1\47\2\0"+
    "\1\47\5\0\2\121\6\0\1\47\7\0\1\122\3\47"+
    "\3\0\1\47\6\0\1\47\2\0\1\47\1\0\1\47"+
    "\1\0\1\47\1\0\1\122\17\0\1\20\54\0\11\52"+
    "\1\123\57\52\2\53\1\124\66\53\16\0\1\125\3\126"+
    "\102\0\1\127\1\0\1\130\14\0\1\131\7\0\1\130"+
    "\24\0\2\56\13\0\1\127\1\0\1\130\14\0\1\131"+
    "\7\0\1\130\20\0\2\132\2\0\1\133\3\134\102\0"+
    "\1\135\1\0\1\136\24\0\1\136\41\0\1\137\16\0"+
    "\1\140\34\0\1\141\3\142\5\0\3\142\6\0\6\142"+
    "\60\0\1\136\14\0\1\135\7\0\1\136\24\0\4\23"+
    "\5\0\10\23\1\0\21\23\1\143\6\23\17\0\4\23"+
    "\5\0\10\23\1\0\4\23\1\144\23\23\17\0\4\23"+
    "\5\0\10\23\1\0\20\23\1\145\7\23\17\0\4\23"+
    "\5\0\10\23\1\0\1\101\27\23\17\0\4\23\5\0"+
    "\10\23\1\0\14\23\1\146\13\23\17\0\4\23\5\0"+
    "\10\23\1\0\5\23\1\147\22\23\17\0\4\23\5\0"+
    "\10\23\1\0\22\23\1\150\5\23\17\0\4\23\5\0"+
    "\10\23\1\0\22\23\1\151\5\23\17\0\4\23\5\0"+
    "\10\23\1\0\21\23\1\152\6\23\17\0\4\23\5\0"+
    "\10\23\1\0\15\23\1\153\12\23\17\0\4\23\5\0"+
    "\10\23\1\0\17\23\1\102\10\23\17\0\4\23\5\0"+
    "\10\23\1\0\12\23\1\154\6\23\1\102\6\23\17\0"+
    "\4\23\5\0\10\23\1\0\14\23\1\155\13\23\17\0"+
    "\4\23\5\0\10\23\1\0\6\23\1\156\11\23\1\157"+
    "\1\160\6\23\17\0\4\23\5\0\10\23\1\0\15\23"+
    "\1\161\12\23\17\0\4\23\5\0\10\23\1\0\6\23"+
    "\1\162\20\23\1\163\17\0\4\23\5\0\10\23\1\0"+
    "\1\164\16\23\1\165\10\23\17\0\4\23\5\0\10\23"+
    "\1\0\10\23\1\166\17\23\17\0\4\23\5\0\10\23"+
    "\1\0\16\23\1\167\11\23\17\0\4\23\5\0\10\23"+
    "\1\0\10\23\1\170\7\23\1\171\7\23\17\0\4\23"+
    "\5\0\10\23\1\0\10\23\1\172\1\23\1\173\15\23"+
    "\17\0\4\23\5\0\10\23\1\0\10\23\1\174\17\23"+
    "\17\0\4\45\5\0\3\45\6\0\6\45\33\0\1\120"+
    "\5\0\2\175\67\0\4\176\5\0\3\176\6\0\6\176"+
    "\23\0\11\52\1\177\3\52\1\124\53\52\30\0\1\57"+
    "\1\60\1\0\1\60\10\0\1\57\1\60\4\0\1\60"+
    "\34\0\4\126\6\0\1\57\1\60\1\0\1\60\10\0"+
    "\1\57\1\60\4\0\1\60\51\0\1\200\1\0\1\201"+
    "\24\0\1\201\41\0\1\202\16\0\1\203\53\0\1\201"+
    "\14\0\1\200\7\0\1\201\24\0\1\133\3\134\100\0"+
    "\1\60\1\0\1\204\1\0\1\205\7\0\1\60\4\0"+
    "\1\206\7\0\1\205\24\0\4\134\7\0\1\60\1\0"+
    "\1\204\1\0\1\205\7\0\1\60\4\0\1\206\7\0"+
    "\1\205\43\0\1\136\24\0\1\136\22\0\1\54\13\0"+
    "\1\57\1\60\1\0\1\60\10\0\1\57\1\60\4\0"+
    "\1\60\32\0\1\54\13\0\1\57\1\60\1\0\1\136"+
    "\10\0\1\57\1\60\4\0\1\60\32\0\1\54\13\0"+
    "\1\57\1\60\1\0\1\60\10\0\1\57\1\60\4\0"+
    "\1\136\32\0\1\207\14\0\1\60\1\0\1\210\1\57"+
    "\1\211\7\0\1\60\4\0\1\212\3\0\1\57\3\0"+
    "\1\211\22\0\1\207\1\0\4\142\5\0\3\142\1\0"+
    "\1\210\1\57\1\211\2\0\6\142\4\0\1\212\3\0"+
    "\1\57\3\0\1\211\24\0\4\23\5\0\10\23\1\0"+
    "\15\23\1\102\12\23\17\0\4\23\5\0\10\23\1\0"+
    "\1\213\27\23\17\0\4\23\5\0\10\23\1\0\4\23"+
    "\1\102\23\23\17\0\4\23\5\0\10\23\1\0\20\23"+
    "\1\214\1\215\6\23\17\0\4\23\5\0\10\23\1\0"+
    "\1\216\27\23\17\0\4\23\5\0\10\23\1\0\1\23"+
    "\1\174\26\23\17\0\4\23\5\0\10\23\1\0\13\23"+
    "\1\102\14\23\17\0\4\23\5\0\10\23\1\0\4\23"+
    "\1\217\23\23\17\0\4\23\5\0\10\23\1\0\1\214"+
    "\27\23\17\0\4\23\5\0\10\23\1\0\10\23\1\220"+
    "\17\23\17\0\4\23\5\0\10\23\1\0\6\23\1\102"+
    "\21\23\17\0\4\23\5\0\10\23\1\0\10\23\1\221"+
    "\17\23\17\0\4\23\5\0\10\23\1\0\21\23\1\222"+
    "\6\23\17\0\4\23\5\0\10\23\1\0\22\23\1\217"+
    "\5\23\17\0\4\23\5\0\10\23\1\0\17\23\1\214"+
    "\10\23\17\0\4\23\5\0\10\23\1\0\14\23\1\223"+
    "\13\23\17\0\4\23\5\0\10\23\1\0\4\23\1\224"+
    "\23\23\17\0\4\23\5\0\10\23\1\0\21\23\1\225"+
    "\6\23\17\0\4\23\5\0\10\23\1\0\22\23\1\226"+
    "\5\23\17\0\4\23\5\0\10\23\1\0\21\23\1\227"+
    "\6\23\17\0\4\23\5\0\10\23\1\0\4\23\1\230"+
    "\23\23\17\0\4\23\5\0\10\23\1\0\15\23\1\231"+
    "\12\23\17\0\4\23\5\0\10\23\1\0\10\23\1\232"+
    "\17\23\17\0\4\23\5\0\10\23\1\0\3\23\1\102"+
    "\24\23\17\0\4\23\5\0\10\23\1\0\1\233\27\23"+
    "\17\0\4\23\5\0\10\23\1\0\12\23\1\145\15\23"+
    "\11\0\1\120\5\0\2\47\61\0\1\120\5\0\4\47"+
    "\5\0\3\47\6\0\6\47\23\0\11\52\1\177\3\52"+
    "\1\0\53\52\35\0\1\201\24\0\1\201\41\0\1\201"+
    "\107\0\1\201\51\0\1\234\1\0\1\235\24\0\1\235"+
    "\41\0\1\236\16\0\1\237\53\0\1\235\14\0\1\234"+
    "\7\0\1\235\24\0\1\240\3\241\5\0\3\241\6\0"+
    "\6\241\56\0\1\242\1\0\1\243\24\0\1\243\41\0"+
    "\1\244\16\0\1\245\53\0\1\243\14\0\1\242\7\0"+
    "\1\243\24\0\4\23\5\0\10\23\1\0\11\23\1\102"+
    "\16\23\17\0\4\23\5\0\10\23\1\0\21\23\1\102"+
    "\6\23\17\0\4\23\5\0\10\23\1\0\10\23\1\246"+
    "\17\23\17\0\4\23\5\0\10\23\1\0\22\23\1\247"+
    "\5\23\17\0\4\23\5\0\10\23\1\0\17\23\1\231"+
    "\10\23\17\0\4\23\5\0\10\23\1\0\14\23\1\145"+
    "\13\23\17\0\4\23\5\0\10\23\1\0\20\23\1\250"+
    "\7\23\17\0\4\23\5\0\10\23\1\0\17\23\1\251"+
    "\10\23\17\0\4\23\5\0\10\23\1\0\4\23\1\172"+
    "\23\23\17\0\4\23\5\0\10\23\1\0\15\23\1\252"+
    "\12\23\17\0\4\23\5\0\10\23\1\0\10\23\1\253"+
    "\17\23\17\0\4\23\5\0\10\23\1\0\2\23\1\214"+
    "\25\23\17\0\4\23\5\0\10\23\1\0\2\23\1\254"+
    "\25\23\17\0\4\23\5\0\10\23\1\0\3\23\1\255"+
    "\24\23\17\0\4\23\5\0\10\23\1\0\14\23\1\102"+
    "\13\23\17\0\4\23\5\0\10\23\1\0\6\23\1\162"+
    "\21\23\17\0\4\23\5\0\10\23\1\0\21\23\1\115"+
    "\6\23\36\0\1\235\24\0\1\235\37\0\1\60\1\0"+
    "\1\60\11\0\1\60\4\0\1\60\47\0\1\60\1\0"+
    "\1\235\11\0\1\60\4\0\1\60\47\0\1\60\1\0"+
    "\1\60\11\0\1\60\4\0\1\235\47\0\1\60\1\0"+
    "\1\60\1\57\10\0\1\60\4\0\1\60\3\0\1\57"+
    "\30\0\4\241\5\0\3\241\1\0\1\60\1\57\3\0"+
    "\6\241\4\0\1\60\3\0\1\57\47\0\1\243\24\0"+
    "\1\243\22\0\1\207\14\0\1\60\1\0\1\60\1\57"+
    "\10\0\1\60\4\0\1\60\3\0\1\57\26\0\1\207"+
    "\14\0\1\60\1\0\1\243\1\57\10\0\1\60\4\0"+
    "\1\60\3\0\1\57\26\0\1\207\14\0\1\60\1\0"+
    "\1\60\1\57\10\0\1\60\4\0\1\243\3\0\1\57"+
    "\30\0\4\23\5\0\10\23\1\0\14\23\1\256\13\23"+
    "\17\0\4\23\5\0\10\23\1\0\12\23\1\214\15\23"+
    "\17\0\4\23\5\0\10\23\1\0\21\23\1\257\6\23"+
    "\17\0\4\23\5\0\10\23\1\0\10\23\1\226\17\23"+
    "\17\0\4\23\5\0\10\23\1\0\5\23\1\102\22\23"+
    "\17\0\4\23\5\0\10\23\1\0\2\23\1\102\25\23"+
    "\17\0\4\23\5\0\10\23\1\0\7\23\1\102\20\23"+
    "\17\0\4\23\5\0\10\23\1\0\4\23\1\252\23\23"+
    "\17\0\4\23\5\0\10\23\1\0\22\23\1\145\5\23"+
    "\17\0\4\23\5\0\10\23\1\0\4\23\1\101\23\23"+
    "\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[9405];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\1\1\11\2\1\1\11\11\1\1\11\24\1"+
    "\10\0\2\1\1\0\1\11\1\1\2\0\1\1\2\0"+
    "\30\1\1\0\1\11\3\0\1\11\2\1\4\0\2\1"+
    "\1\0\37\1\4\0\1\11\3\1\1\0\1\1\1\0"+
    "\1\1\1\0\22\1\1\0\5\1\1\0\15\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[175];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  @SuppressWarnings("unused")
  private boolean zzEOFDone;

  /* user code: */

    public int line = 1;

    public enum TokenType{
        KEYWORD, IDENTIFIER, INTEGER, FLOAT,
        CHAR, STRING, OPERATOR, BOUND, BLANK, ANNOTATION;
    };

    public int getYyline(){
        return yyline;
    }

    public int getYycolumn(){
        return yycolumn;
    }



  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Scanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  public String yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
              {
                return "EOF";
              }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { return TokenType.BLANK.name();
            }
            // fall through
          case 12: break;
          case 2:
            { ++line;
            }
            // fall through
          case 13: break;
          case 3:
            { return TokenType.OPERATOR.name();
            }
            // fall through
          case 14: break;
          case 4:
            { return TokenType.BOUND.name();
            }
            // fall through
          case 15: break;
          case 5:
            { return TokenType.INTEGER.name();
            }
            // fall through
          case 16: break;
          case 6:
            { return TokenType.IDENTIFIER.name();
            }
            // fall through
          case 17: break;
          case 7:
            { return TokenType.FLOAT.name();
            }
            // fall through
          case 18: break;
          case 8:
            { return TokenType.KEYWORD.name();
            }
            // fall through
          case 19: break;
          case 9:
            { return TokenType.STRING.name();
            }
            // fall through
          case 20: break;
          case 10:
            { return TokenType.CHAR.name();
            }
            // fall through
          case 21: break;
          case 11:
            { return TokenType.ANNOTATION.name();
            }
            // fall through
          case 22: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
