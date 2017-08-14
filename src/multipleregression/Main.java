package multipleregression;

import utils.datasets.Datasets;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Luis
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Multiple Regression Pre-Processing w/ Backward Elimination"
                + System.lineSeparator()
                + "Researchers: Anton Kovalyov, Luis Garay"
                + System.lineSeparator()
                + "Supervisor: Dr. Hansheng Lei"
                + System.lineSeparator());

        String t = "patientId,value0,value1,value2,value3,value4,value5,value6,value7,value8,value9,value10,value11,value12,value13,value14,value15,value16,value17,value18,value19,value20,value21,value22,value23,value24,value25,value26,value27,value28,value29,value30,value31,value32,value33,value34,value35,value36,value37,value38,value39,value40,value41,value42,value43,value44,value45,value46,value47,value48,value49,value50,value51,value52,value53,value54,value55,value56,value57,value58,value59,value60,value61,value62,value63,value64,value65,value66,value67,value68,value69,value70,value71,value72,value73,value74,value75,value76,value77,value78,value79,value80,value81,value82,value83,value84,value85,value86,value87,value88,value89,value90,value91,value92,value93,value94,value95,value96,value97,value98,value99,value100,value101,value102,value103,value104,value105,value106,value107,value108,value109,value110,value111,value112,value113,value114,value115,value116,value117,value118,value119,value120,value121,value122,value123,value124,value125,value126,value127,value128,value129,value130,value131,value132,value133,value134,value135,value136,value137,value138,value139,value140,value141,value142,value143,value144,value145,value146,value147,value148,value149,value150,value151,value152,value153,value154,value155,value156,value157,value158,value159,value160,value161,value162,value163,value164,value165,value166,value167,value168,value169,value170,value171,value172,value173,value174,value175,value176,value177,value178,value179,value180,value181,value182,value183,value184,value185,value186,value187,value188,value189,value190,value191,value192,value193,value194,value195,value196,value197,value198,value199,value200,value201,value202,value203,value204,value205,value206,value207,value208,value209,value210,value211,value212,value213,value214,value215,value216,value217,value218,value219,value220,value221,value222,value223,value224,value225,value226,value227,value228,value229,value230,value231,value232,value233,value234,value235,value236,value237,value238,value239,value240,value241,value242,value243,value244,value245,value246,value247,value248,value249,value250,value251,value252,value253,value254,value255,value256,value257,value258,value259,value260,value261,value262,value263,value264,value265,value266,value267,value268,value269,value270,value271,value272,value273,value274,value275,value276,value277,value278,value279,value280,value281,value282,value283,value284,value285,value286,value287,value288,value289,value290,value291,value292,value293,value294,value295,value296,value297,value298,value299,value300,value301,value302,value303,value304,value305,value306,value307,value308,value309,value310,value311,value312,value313,value314,value315,value316,value317,value318,value319,value320,value321,value322,value323,value324,value325,value326,value327,value328,value329,value330,value331,value332,value333,value334,value335,value336,value337,value338,value339,value340,value341,value342,value343,value344,value345,value346,value347,value348,value349,value350,value351,value352,value353,value354,value355,value356,value357,value358,value359,value360,value361,value362,value363,value364,value365,value366,value367,value368,value369,value370,value371,value372,value373,value374,value375,value376,value377,value378,value379,value380,value381,value382,value383,reference";
        System.out.println("Length: " + determineAttributeLength(t));

        Scanner input = new Scanner(System.in);
        boolean choice = true;
        while (choice) {
            System.out.println("Run Multiple Regression w/ GUI - 1"
                    + System.lineSeparator()
                    + "Run Multiple Regression w/o GUI - 2"
                    + System.lineSeparator()
                    + "Stop Program - 5");

            switch (input.next().charAt(0)) {
                case '1':
                    System.out.println("Loading GUI...");
                    MainFX.main(args);
                    break;
                case '2':
                    XP.run(Datasets.DATASETS[3]);
                    choice = false;
                    break;
                case '5':
                    choice = false;
                    break;
                default:
                    System.out.println("Bad Input! Please try again!");
                    break;
            }
        }
    }

    private static int determineAttributeLength(String columnHeader) {
        return StringUtils.split(columnHeader, ",").length;
    }
}
