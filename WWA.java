import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class WWA extends Applet implements Runnable {

//���l�f�t�@�C��
final int ATR_0 = 0;
final int ATR_CROP1 = 1;
final int ATR_CROP2 = 2;
final int ATR_TYPE = 3;
final int ATR_MODE = 4;
final int ATR_STRING = 5;
final int ATR_X = 6;
final int ATR_Y = 7;
final int ATR_X2 = 8;
final int ATR_Y2 = 9;
final int ATR_ENERGY = 10;
final int ATR_STRENGTH = 11;
final int ATR_DEFENCE = 12;
final int ATR_GOLD = 13;
final int ATR_ITEM = 14;
final int ATR_NUMBER = 15;
final int ATR_JUMP_X = 16;
final int ATR_JUMP_Y = 17;
final int ATR_SOUND = 19;
final int ATR_MOVE = 16;

final int MAP_STREET = 0;
final int MAP_WALL = 1;
final int MAP_LOCALGATE = 2;
final int MAP_URLGATE = 4;

final int OBJECT_NORMAL = 0;
final int OBJECT_MESSAGE = 1;
final int OBJECT_URLGATE = 2;
final int OBJECT_STATUS = 3;
final int OBJECT_ITEM = 4;
final int OBJECT_DOOR = 5;
final int OBJECT_MONSTER = 6;
final int OBJECT_SCORE = 11;
final int OBJECT_SELL = 14;
final int OBJECT_BUY = 15;
final int OBJECT_RANDOM = 16;
final int OBJECT_SELECT = 17;
final int OBJECT_LOCALGATE = 18;

final int CONFIG_QLOAD_X = 40*11+10;
final int CONFIG_QLOAD_Y = 325-1;
final int CONFIG_QSAVE_X = 40*11+10;
final int CONFIG_QSAVE_Y = 360-1;
final int CONFIG_RESTART_X = 40*11+9;
final int CONFIG_RESTART_Y = 395-1;

final int YESNO_NONE = 0;
final int YESNO_YES = 1;
final int YESNO_NO = 2;
final int YESNO_WWAH = 3;
final int YESNO_URLGATE = 4;
final int YESNO_RESTART = 5;
final int YESNO_SELL = 6;
final int YESNO_BUY = 7;
final int YESNO_SOUND = 8;
final int YESNO_USEITEM = 9;
final int YESNO_QSAVE = 10;
final int YESNO_QLOAD = 11;
final int YESNO_SELECT = 12;
final int YESNO_TEXTSAVE = 13;
final int YESNO_TEXTLOAD = 14;

final int FRAME_ENERGY_X = 40*11+6;
final int FRAME_ENERGY_Y = 0;
final int FRAME_STRENGTH_X = 40*11+6;
final int FRAME_STRENGTH_Y = 35;
final int FRAME_DEFENCE_X = 40*11+6;
final int FRAME_DEFENCE_Y = 35*2;
final int FRAME_GOLD_X = 40*11+6;
final int FRAME_GOLD_Y = 35*3;

//�f�[�^�ǂݍ��ݗp
final int DATA_CHECK = 0;
final int DATA_MAP_VERSION = 2;

final int DATA_STATUS_ENERGY = 10;
final int DATA_STATUS_STRENGTH = 12;
final int DATA_STATUS_DEFENCE = 14;
final int DATA_STATUS_GOLD = 16;
final int DATA_STATUS_ENERGYMAX = 32;

int DATA_MAP_COUNT;
int DATA_OBJECT_COUNT;
int DATA_CHARA_X;
int DATA_CHARA_Y;
int DATA_OVER_X;
int DATA_OVER_Y;
int DATA_ITEM;

final int EX_DATA_CHARA_X = 38;
final int EX_DATA_CHARA_Y = 40;
final int EX_DATA_OVER_X = 42;
final int EX_DATA_OVER_Y = 44;
final int DATA_IMG_CHARA_CROP = 46;
final int DATA_IMG_YESNO_CROP = 48;
final int EX_DATA_ITEM = 66;

final int DATA_MAP_SIZE = 46;
final int DATA_MES_NUMBER = 48;
final int DATA_CHECK_PARTS = 50;
final int DATA_SAVE_STOP = 52;
final int EX_DATA_STATUS_ENERGYMAX = 54;
final int DATA_FLAG_DEFAULT = 56;
final int DATA_FLAG_OLDMAP = 58;
final int DATA_STEP = 60;
final int DATA_MUSIC = 62;
final int DATA_CROP = 90;
final int DATA_FLAG_DELP = 106;
final int DATA_IMG_CLICK = 108;
final int DATA_EFFECT = 110;
final int DATA_ANMITEM = 120;
final int DATA_VALIABLE = 140;

final int FREAD_BLOCK = 10000;
final int MEM_BLOCK = 65000;
final int SOUND_MAX = 100;

int MAP_ATR_MAX;
int OBJECT_ATR_MAX;

//�L�����N�^�ʒu
int charaX, charaY;
int movePlus = 1;		//�ړ��P��

//���b�Z�[�W�p�o�b�t�@
String mapcgName;
String mojicgName;
String worldPassword;
int worldPassNumber;
String worldName;

//���f�B�A�g���b�J�[
MediaTracker tracker = new MediaTracker( this );
//�e��C���[�W
Image imgMap;
//�C���[�W�؂���p
Image imgCrop[];
int cropID;
int cropIDchara = 4;
//�T�E���h�֌W
AudioClip audio[] = new AudioClip[SOUND_MAX];
boolean soundFlag = false;

//�I�t�X�N���[���p�C���[�W
Image imgBuff;
Image imgBuffMap;
Image imgBuffStatus;
Image imgBuffCharaX;
Image imgBuffCharaY;
Image imgBuffBattle;
Image imgBuffButton;
//�I�t�X�N���[���p
Graphics gBuff;
Graphics gBuffMap;
Graphics gBuffStatus;
Graphics gBuffCharaX;
Graphics gBuffCharaY;
Graphics gBuffBattle;
Graphics gBuffButton;

//�x�d�r�C�m�n�̑I��
int yesnoNumber;
int yesnoJudge;
int yesnoX;
int yesnoY;
boolean yesnoFlag;

//�e��t���O
boolean loadingFlag = false;
boolean scoreFlag;
boolean urlJumpFlag;	//URL�����N���̒ᑬ���[�h
boolean g_bUseUrlJump;
boolean quickSaveFlag = false;
boolean displayMonsterFlag = false;
boolean gFileNotFound;
boolean gImageNotFound;
boolean gDataBroken;
boolean g_bIOError = false;
boolean g_bAudioError = false;
boolean bDispUseItemMes = true;
boolean g_bAttackJudgeMes = true;	//�U���s�̏������s����
boolean g_bDisplayCenter = false;
boolean g_bJREFont = false;
boolean g_bRefuseExtend = false;

//�L�[����
boolean UpKey, DownKey, LeftKey, RightKey;
int currentKey;
boolean inputKey = true;

//�^�C�}�̊��o
Thread timer;
int countAnimation;
int countRepaint;
int waitCounterLast = 0;
int g_iRepaintCount = 10;
int g_iRepaintRetry = 0;

//�}�b�v�ʒu
int mdata;
int mdataBuff;
int XpBuff;
int YpBuff;

//�}�b�v���������p�t���O
boolean mapFlag[][] = new boolean[11][11];
boolean mapFlagAll;
boolean mapFlagErase;
boolean flagDisplayStatus;

int movingSkip;		//�ړ��������X�L�b�v�����
boolean repaintSkip;
int itemboxBuff = -1;
int g_iUseItem = 0;

//�X�e�[�^�X
int itemStrength;
int itemDefence;
long score;

//�e�����A�T�C�Y
int g_iMapWidth = 101;
int g_iCropMax;
int g_iMapPartsMax;
int g_iObjectPartsMax;
int g_iMesNumberMax;

//�����X�^�[�U���p
int monsterEnergy;
int monsterStrength;
int monsterDefence;
int monsterGold;
int attackXp;
int attackYp;
boolean attackFlag;
boolean attackFlagTurn;
int g_iAttackTurn;

//�`�惂�[�h
boolean g_bFadeBlack = false;
boolean g_bRestPlayer = true;
boolean g_bMapFlagMove = false;

int g_iMapObjMove[][][] = new int[13][13][2];	//�ړ��A�j���p�t���O
int g_iTurnSkip = 0;	//�^�[���X�L�b�v
boolean g_bPopup = false;
int g_iRestCount = 0;	//�����͂̎���

//�t�@�C�����o��
byte byteBuff[]; // = new byte[MEM_BLOCK];
byte byteBuffPress[] = new byte[MEM_BLOCK];

//�N�C�b�N�Z�[�u�p
int QSaveParameter[] = new int[200];
short QSaveMap[][];
short QSaveMapObject[][];
int QSaveObjectAttribute[][];
int QSaveMapAttribute[][];
//�f�[�^�ۑ��p
byte PressData[];
int g_bOldMap = 0;

//��Ɨp
int g_iYesnoY;
int g_iPointer;
int g_iBlockByteBuff = 1;
int g_iBlockByteBuffPress = 1;
int g_iFileSize = 0;
int g_iPointerExtract;
boolean g_bCompleteExtract;
int g_iPageNumber = 0;	//���y�[�W�p
int g_iLast;	//�g�[�N���|�C���^
Random random = new Random();	//�����_��

//�G�t�F�N�g�p
int g_iEffWait = 0;
int g_iEffCrop[] = new int[4];
int g_iEffCurrent = 0;
int g_iAnmItemBox;
int g_iAnmItemOld;
int g_iAnmItemCount = 0;
int g_iAnmItemX;
int g_iAnmItemY;
int g_iAnmItemCountAdd = 0;
int g_iChangeStatus[] = new int[4];

//V3.1�ǉ���
boolean g_bSkipPaint = false;	//���A�j���[�V�����␳�p

////static�ϐ�
//�e�A�C�R���̃N���b�v�ԍ�
 int CROP_YES = 13;
 int CROP_NO = 14;
 int CROP_YES2 = 15;
 int CROP_NO2 = 16;
 int CROP_ENERGY = 23;
 int CROP_STRENGTH = 24;
 int CROP_DEFENCE = 25;
 int CROP_GOLD = 26;
 int CROP_BOM = 33;
 int CROP_STFRAME = 34;
 int CROP_MAINFRAME = 10;
 int CROP_ITEMFRAME = 21;
//�L�����N�^�ʒu
 int playerX, playerY;
 char moveDirect = 2;	//�ړ�����
//���b�Z�[�W�p�o�b�t�@
 String strMessage[];
 String g_szMessageSystem[]  = new String[20];
 int strNumber;
//�摜�ʒu
 int g_iImgCharaCrop = 2;
 int g_iImgClickItem = 0;
//�}�b�v�p�o�b�t�@
 int mapX, mapY;
 short map[][];
 short mapObject[][];
//�w�i�A���̃L�����N�^�f�[�^
 int objectAttribute[][];
 int mapAttribute[][];

 int statusEnergy;
 int statusEnergyMax;
 int statusStrength;
 int statusDefence;
 int statusGold;
 int itemBox[] = new int[12];
 int g_iValiable[] = new int[100];
 int gameoverXp;	//�Q�[���I�[�o�[
 int gameoverYp;

 int TimerCount = 20;
 boolean g_bPaintMapMove = true;
 int g_iStep = 0;
 int g_iMusicNumber = 0;

 boolean messageFlag;
 boolean configFlag;
 boolean g_bMapFlagAllB;
 boolean g_bJump = false;
 boolean g_bStopInput = false;
 String g_szCopyRight;
 int g_bSaveStop = 0;
 int g_bDefault = 0;
 int g_bAnmItem = 1;

 boolean g_bNoExec = false;
 int g_iXpoint, g_iYpoint;
 boolean g_bReturnMessage = false;
 String g_szReturnMessage;
// int g_iOldPartsObject;	//�d������p
 boolean g_bPlayAudio = false;
 int g_bDelPlayer = 0;
 boolean g_bGameOver = false;

// �ǂݍ��ރt�@�C����
static String mapName;

//////////////////////////////////////////////////
//���C���֐�
public static void main(String[] args){
	mapName = args[0];
	Frame frame = new Frame("WWA");
	WWA wwaf = new WWA();
	frame.add("Center", wwaf);
	frame.pack();
	wwaf.init();
	frame.setSize(560, 440);
	frame.show();
	wwaf.start();
}

//�V�X�e��������

public void init()
{
	soundFlag = false;
	attackFlag = false;
	scoreFlag = false;
	urlJumpFlag = false;

	//�e��f�[�^������
	inputKey = true;
	yesnoNumber = YESNO_NONE;
	yesnoJudge = YESNO_NONE;
	mapFlagErase = false;
	messageFlag = false;
	configFlag = false;
	UpKey = false;
	DownKey = false;
	LeftKey = false;
	RightKey = false;
	countRepaint = 0;
	movingSkip = 0;
	repaintSkip = false;
	currentKey = 0;
	mapFlagAll = true;
	gFileNotFound = false;
	gImageNotFound = false;
	gDataBroken = false;
	PressData = null;
	timer = null;

	//�I�t�X�N���[���쐬
	imgBuff = createImage( 40*11, 40*11 );
	gBuff = imgBuff.getGraphics();
	imgBuffStatus = createImage( 40*3, 40*11 );
	gBuffStatus = imgBuffStatus.getGraphics();
	imgBuffMap = createImage( 40, 40 );
	gBuffMap = imgBuffMap.getGraphics();
	imgBuffCharaX = createImage( 40*2, 40 );
	gBuffCharaX = imgBuffCharaX.getGraphics();
	imgBuffCharaY = createImage( 40, 40*2 );
	gBuffCharaY = imgBuffCharaY.getGraphics();
	imgBuffBattle = createImage( 340, 60 );
	gBuffBattle = imgBuffBattle.getGraphics();
	imgBuffButton = createImage( 120, 35 );
	gBuffButton = imgBuffButton.getGraphics();

	//�t�H���g�T�C�Y����t�H���g�^�C�v����
	judgeFontAttribute();

	System.out.println( "<init>" );
}


public void start()
{
	//�^�C�}�n��
	if( timer == null ){
		timer = new Thread(this);
		timer.start();
	}
	System.out.println( "<start>" );
}


public void stop()
{
	System.out.println( "<stop>" );
}


public void destroy()
{
	int i;

	//�w�i���y��~
	playAudio( 99 );
	//�^�C�}��~
	if( timer != null ) timer.stop();
	//timer = null;
	//�I�t�X�N���[���j��
	gBuff.dispose();
	gBuffStatus.dispose();
	gBuffMap.dispose();
	gBuffCharaX.dispose();
	gBuffCharaY.dispose();
	gBuffBattle.dispose();
	//�摜���
	for( i = 0 ; i < g_iCropMax ; ++i ) imgCrop[i].flush();
	//���������
	System.gc();

	System.out.println( "<destroy>" );
}



//////////////////////////////////////////////////
//�^�C�}����

public void run()
{
	while( timer.isAlive() ){
		try {
			if( urlJumpFlag == true ) Thread.sleep( 500 );
			if( (yesnoNumber == YESNO_SOUND) || (loadingFlag == false) ) Thread.sleep( 200 );
			else Thread.sleep( TimerCount );
		} catch( InterruptedException e ){
			System.err.println("111 Thread Error!");
		}
		//�ĕ`��
		if( repaintSkip == false ) repaint();
	}
}



//////////////////////////////////////////////////
//�`�揈��

public void update( Graphics g )
{
	paint(g);
}

public void paint( Graphics g )
{
	//�ϐ���`
	int i, j;
	int x, y;
	int number;

	//�G���[�`�F�b�N
	if( ErrorCheck(g) == true ) return;

	//�f�[�^��ǂݍ���ł��Ȃ��ꍇ
	if( loadingFlag == false ){
		//�}�b�v�f�[�^�ǂݍ���
		LoadingMessage( g, 0 );
		//loadMapData( g, getParameter("paramMapName"), true );
		loadMapData( g, mapName, true );
		if( (gFileNotFound == true) || (g_bIOError == true) ) return;
		//�摜�ǂݍ���
		LoadingMessage( g, 1 );
		graphicLoading( g );
		if( gImageNotFound == true ) return;
		twait( 500 );
		//�T�E���h�f�[�^
		yesnoNumber = YESNO_SOUND;
		yesnoFlag = true;
		//gBuffX.setColor( Color.white );
		//gBuffX.fillRect( 0, 0, 560, 440 );
		loadingFlag = true;
	}

	//�`�F�b�N�{�b�N�X�̊m�F
	if( (g_cbSavePassword.getState() == true) || (g_cbLoadPassword.getState() == true) ){
		if( g_bOpenLoadPass == true ) ExtractLoadMapData();
		g_frameWin.dispose();
		g_cbSavePassword.setState( false );
		g_cbLoadPassword.setState( false );
		g_bOpenFrameWin = false;
		urlJumpFlag = false;
		mapFlagAll = true;
		mapFlagErase = false;
		g_iRepaintRetry = 3;
	}
	//�����X�^�[�̔\�͒l�\��
	if( displayMonsterFlag == true ){
		DisplayMonsterData( g );
	}
	//�L�[���͑҂��Ȃ�΃o�b�N�o�b�t�@���珑������
	if( inputKey == false ){
		waitCounterLast = 200;
		g.drawImage( imgBuff, 0, 0, this );
		g.drawImage( imgBuffStatus, 40*11, 0, this );
		return;
	}
	//�x�d�r�C�m�n�̔���
	if( yesnoJudge(g) == false ) return;
	if( yesnoFlag == true ) return;

	//�X�e�[�^�X�\���t���O
	if( flagDisplayStatus == true ){
		displayConfigWindow( g, true, true, true, true );
		flagDisplayStatus = false;
	}
	//�U����Ԃ̏ꍇ
	if( attackFlag == true ){
		attackMonster( g, attackXp, attackYp );
		return;
	}
	//�L�����N�^�ړ�
	if( (mapFlagAll != true) && (inputKey != false) && (attackFlag != true) ){
		if( movingSkip != 0 ) --movingSkip;
		else moveCharacter(g);
	}
	//�Q�[���I�[�o�[
	if( g_bGameOver == true ) GameOver( g );
	//�ʒu�ύX
	if( g_bJump == true ) JumpPoint( playerX, playerY );
	//�ȕύX
	if( g_bPlayAudio == true ) playAudio( g_iMusicNumber );
	//�v���[���[�̌��݈ʒu�擾
	playerX = charaX /5 +mapX *10;
	playerY = charaY /5 +mapY *10;

	if( g_iRepaintRetry > 0 ){
		mapFlagAll = true;
		mapFlagErase = false;
		--g_iRepaintRetry;
	}
	//�}�b�v�S�̏�������
	if( mapFlagAll == true ){
		repaintSkip = true;
		paintMapAll( g, true );
		repaintSkip = false;
		movingSkip = 3;
	}
	if( g_bMapFlagAllB == true ){
		g_bMapFlagAllB = false;
		paintMapAll( g, true );
	}
	//��`�E�B���h�E�̕`��
	if( configFlag == true ){
		displayConfigWindow( g, true, true, true, true );
		arrangeItem( 0 );
		configFlag = false;
	}
	//�}�b�v�ƃL�����N�^�̕`��
	paintMap( g );
	//�}�b�v�������������̃t���O�Z�b�g
	setMapFlag();
	g_bSkipPaint = false;

	//�E�F�C�g
	if( waitCounterLast != 0 ){
		twait( waitCounterLast );
		waitCounterLast = 0;
	}
	//���{��̕\��
	if( (messageFlag == false) && (g_bReturnMessage == true) ){
		messageFlag = true;
		g_bReturnMessage = false;
		strNumber = 1;
		//g_iOldPartsObject = 0;
	}
	if( messageFlag == true ) MessageCheck( g );

	//�X�R�A�̕\��
	if( scoreFlag == true ){
		displayScore( gBuff );
		scoreFlag = false;
	}
	//�����{�^���`��N���A
	if( (g_iUseItem > 0) && (inputKey != false) ){
		g_iUseItem = 0;
		itemboxBuff = -1;
		twait( 100 );
		displayConfigWindow( g, true, false, true, false );
	}
	for( i = 0 ; i < 4 ; ++i ){
		if( (g_iChangeStatus[i] == 1) && (g_bAnmItem == 1) ) configFlag = true;
		if( g_iChangeStatus[i] > 0 ) --g_iChangeStatus[i];
	}
}


//���b�Z�[�W�`�F�b�N
public void MessageCheck( Graphics g )
{
	messageFlag = false;
	if( g_iPageNumber == -1 ) g_iPageNumber = 0;

	if( !((strMessage[strNumber].equals("")) || (strNumber == 0)) ){
		int Ytop;
		if( scoreFlag == true ) Ytop = 80;
		else if( g_bDisplayCenter == true ) Ytop = -3;
		else if( charaY /5 >= 6 ) Ytop = -2;
		else Ytop = -1;
		
		//����`�������p
		if( drawJapaneseFrame( gBuff, g, strMessage[strNumber], 50, Ytop ) == false ) return;
		
		inputKey = false;
		mapFlagAll = true;
		mapFlagErase = false;
		movingSkip = 3;
	}
	g_bDisplayCenter = false;
}


//�ǂݍ��ݒ��̃��b�Z�[�W
public void LoadingMessage( Graphics g, int Number )
{
	Font fon;

	g.setColor(Color.white);
	g.fillRect( 0, 0, 40*14, 40*11 );

	g.setColor(Color.black);
	fon = new Font("TimesRoman", Font.PLAIN, 32 );
	g.setFont(fon);
	g.drawString( "Welcome to WWA!" ,100,70 );

	fon = new Font("TimesRoman", Font.PLAIN, 18 );
	g.setFont(fon);
	g.drawString( "(C)1996-2015  NAO   Ver 3.11" ,160, 390 );

	fon = new Font("TimesRoman", Font.PLAIN, 22 );
	g.setFont(fon);

	if( Number >= 0 ){
		g.drawString( "Now Map data Loading ....." ,50,140 );
	}
	if( Number >= 1 ){
		g.drawString( "Now Map data Loading ..... Complete!" ,50,140 );
		g.drawString( "Now CG data Loading ....." ,50,170 );
	}
	if( Number >= 3 ){
		g.drawString( "Now CG data Loading ..... Complete!" ,50,170 );
		g.drawString( "Now Making chara CG ....." ,50,200 );
	}
	if( Number >= 4 ){
		g.drawString( "Now Making chara CG ..... Complete!" ,50,200 );
	}
	if( Number >= 1 ){
		//���[���h���\��
		fon = new Font("TimesRoman", Font.PLAIN, 18 );
		g.setFont(fon);
		g.drawString( "World Name   " +worldName, 160 ,360 );
	}
}



//////////////////////////////////////////////////
//������𐔒l�ϊ�

public int parse( String str )
{
	int number = 0;

	try{
		number = Integer.parseInt( str );
	} catch( NumberFormatException e ){};

	return number;
}



//////////////////////////////////////////////////
//�G���[�`�F�b�N

public boolean ErrorCheck( Graphics g )
{
	if( (gFileNotFound == true) || (gImageNotFound == true) || (gDataBroken == true) || (g_bIOError == true) || (g_bRefuseExtend == true) ){
		g.setColor( Color.white );
		g.fillRect( 0, 0, 560, 440 );
		g.setColor(Color.black);
		Font fon = new Font("TimesRoman", Font.PLAIN, 16 );
		g.setFont(fon);
		if( gDataBroken == true ){
			g.drawString( "�}�b�v�f�[�^�����Ă��܂��B" ,10,180 );
			g.drawString( "�e�L�X�g���[�h�ő��M���Ă��Ȃ����m�F���Ă��������B" ,10,210 );
		} else if( gFileNotFound == true ){
			// g.drawString( "�}�b�v�f�[�^�t�@�C�� " +getParameter("paramMapName") +" ��������܂���B" ,10,180 );
			g.drawString( "�}�b�v�f�[�^�t�@�C�� " +mapName +" ��������܂���B" ,10,180 );
			g.drawString( "�f�[�^���A�b�v���[�h����Ă��邩�m�F���Ă��������B" ,10,210 );
		} else if( gImageNotFound == true ){
			if( mapcgName.equals("") ){
				g.drawString( "�摜�f�[�^�t�@�C������������܂���B" ,10,180 );
				g.drawString( "�}�b�v�f�[�^�����Ă��܂��B" ,10,210 );
			} else {
				g.drawString( "�摜�f�[�^�t�@�C�� " +mapcgName +" ��������Ȃ����A�N�Z�X�ł��܂���B" ,10,180 );
				g.drawString( "�f�[�^���A�b�v���[�h����Ă��邩�A" ,10,210 );
				g.drawString( "�p�[�~�b�V�������ǂݍ��݉ɂȂ��Ă��邩�Ȃǂ��m�F���Ă��������B" ,10,240 );
			}
		} else if( g_bIOError == true ){
			g.drawString( "�}�b�v�f�[�^�ɃA�N�Z�X�ł��܂���B" ,10,180 );
			g.drawString( "�p�[�~�b�V�������ǂݍ��݉ɂȂ��Ă��邩�Ȃǂ��m�F���Ă��������B" ,10,210 );
		} else if( g_bRefuseExtend == true ){
			g.drawString( "�Â��}�b�v��Ïؔԍ����S���ȏ�̃}�b�v�ɂ͊g���}�N���͎g���܂���B" ,10,180 );
		}
		twait( 1000 );
		return true;
	}
	return false;
}



//////////////////////////////////////////////////
//�x�����C�m������

public void yesnoJudgeSub( Graphics g, Graphics gGlobal, String str, int x, int y, int yx )
{
	//�����t��
	if( y == 0 ) y = -3;

	if( yesnoFlag == true ){
		yesnoX = yx;
		if( yesnoNumber == YESNO_SOUND ){
			gBuff.setColor( Color.white );
			gBuff.fillRect( 0, 0, 440, 440 );
			gBuffStatus.setColor( Color.white );
			gBuffStatus.fillRect( 0, 0, 120, 440 );
			drawJapaneseFrame( gBuff, null, str, x, y );
		} else {
			drawJapaneseFrame( g, gGlobal, str, x, y );
		}
		yesnoY = g_iYesnoY;
		yesnoFlag = false;
	}
}


public void playAudio( int number )
{
	if( g_iMusicNumber == number ) return;

	//�w�i���y��~
	if( (number == 99) && (g_iMusicNumber != 0) && (audio[g_iMusicNumber] != null) && (soundFlag == true) ){
		audio[g_iMusicNumber].stop();
		g_iMusicNumber = 0;
		return;
	}
	//���[�h���̉��y�ĊJ
	if( (number == 100) && (g_iMusicNumber != 0) && (audio[g_iMusicNumber] != null) && (soundFlag == true) ){
		audio[g_iMusicNumber].loop();
	}
	//���t
	if( (number != 0) && (number < SOUND_MAX) && (audio[number] != null) && (soundFlag == true) ){
		if( number < 70 ){
			audio[number].play();
		} else {
			//�w�i���y
			if( (g_iMusicNumber != 0) && (audio[g_iMusicNumber] != null) ) audio[g_iMusicNumber].stop();
			g_iMusicNumber = number;
			audio[g_iMusicNumber].loop();
		}
	}
}


public void getAudioClipSub( int number )
{
	if( number < SOUND_MAX ){
		try {
			audio[number] = getAudioClip( getDocumentBase(), number+".au" );
		} catch( Exception e ){
			System.err.println( "150 Audio Error!" );
			g_bAudioError = true;
		}
	}
}


public void SaveStopMes()
{
	DisplayMessage( "�����ł̓Z�[�u�@�\��\n�g�p�ł��܂���B", true );
	yesnoNumber = YESNO_NONE;
	yesnoJudge = YESNO_NONE;
	yesnoFlag = false;
}


public boolean yesnoJudge( Graphics g )
{
	//�ϐ���`
	int i, j;
	int x, y;
	int number;
	int Ytop;
	if( charaY /5 >= 6 ) Ytop = -2;
	else Ytop = -1;
	int iBoxPos;

	//�A�C�e���g�p���b�Z�[�W�̕\����
	if( (yesnoNumber == YESNO_USEITEM) && (strMessage[8].equals("BLANK")) ){
		UseItem( g );
		yesnoNumber = YESNO_NONE;
		yesnoFlag = false;
	}

	if( yesnoNumber != YESNO_NONE ){
		//�{�^���\��
		if( yesnoFlag == false ){
			gBuffMap.setColor( Color.white );
			gBuffMap.fillRect( 0, 0, 40, 40 );
			if( yesnoJudge == YESNO_YES ){
				gBuffMap.drawImage( imgCrop[CROP_YES2], 0, 0, this );
				gBuff.drawImage( imgBuffMap, yesnoX+2, yesnoY+2, this );
			}
			gBuffMap.setColor( Color.white );
			gBuffMap.fillRect( 0, 0, 40, 40 );
			if( yesnoJudge == YESNO_NO ){
				gBuffMap.drawImage( imgCrop[CROP_NO2], 0, 0, this );
				gBuff.drawImage( imgBuffMap, yesnoX+2 +40, yesnoY+2, this );
			}
			g.drawImage( imgBuff, 0, 0, this );
			g.drawImage( imgBuffStatus, 40*11, 0, this );
			twait( 100 );
			
			//�T�E���h
			if( (yesnoJudge == YESNO_YES) || (yesnoJudge == YESNO_NO) ){
				playAudio( 1 );
			}
		}
		//�v�v�`�T�C�g�ɃW�����v
		if( yesnoNumber == YESNO_WWAH ){
			yesnoJudgeSub( gBuff, g, "�v�v�`�̌����T�C�g���J���܂����H", 50, 0, 40*7 +16 );
			
			if( yesnoJudge == YESNO_YES ){
				try{
					urlJumpFlag = true;
					URL url = new URL( "http://www.wwajp.com/" );
					if( g_bPopup == true ) getAppletContext().showDocument( url, "new" );
					else getAppletContext().showDocument( url );
				} catch( MalformedURLException e ){
					System.err.println( "141 URL Error!" );
				}
			}
		}
		//�t�q�k�����N
		if( yesnoNumber == YESNO_URLGATE ){
			if( strMessage[5].equals("") ) yesnoJudgeSub( gBuff, g, "���̃y�[�W�Ƀ����N���܂��B\n��낵���ł����H", 50, 0, 40*7 +16 );
			else yesnoJudgeSub( gBuff, g, strMessage[5], 50, 0, 40*7 +16 );
			if( yesnoJudge == YESNO_YES ) jumpURL( strMessage[strNumber], false );
		}
		//�ăX�^�[�g
		if( yesnoNumber == YESNO_RESTART ){
			yesnoJudgeSub( gBuff, g, "���߂���X�^�[�g���Ȃ����܂����H", 50, 0, 40*7 +16 );
			if( yesnoJudge == YESNO_YES ){
				loadMapData( g, "", false );
				//moveDirect = 2;
				g_iImgCharaCrop = 2;
				SetDirectPlayer( 2 );
				SetYesNoCrop( 13 );
				g_bSaveStop = 0;
				g_bDefault = 0;
				g_bOldMap = 0;
				g_iStep = 0;
				g_iEffWait = 0;
				g_iImgClickItem = 0;
				g_bAnmItem = 1;
				g_bFadeBlack = true;
				//���b�Z�[�W�N���A
				messageFlag = false;
				g_iPageNumber = 0;
				//�w�i���y��~
				playAudio( 99 );
				g_bRestPlayer = true;
			}
		}
		//���𔄂�L�����N�^
		if( yesnoNumber == YESNO_SELL ){
			yesnoJudgeSub( gBuff, g, strMessage[strNumber], 50, Ytop, 40*7 +16 );
			
			if( yesnoJudge == YESNO_YES ){
				int item = objectAttribute[mdataBuff][ATR_ITEM];
				
				for( i = 0 ; i < 12 ; ++i ){
					if( itemBox[i] == 0 ) break;
				}
				iBoxPos = objectAttribute[item][ATR_NUMBER];
				if( (i == 12) && (item != 0) && ((iBoxPos == 0) || (objectAttribute[itemBox[iBoxPos-1]][ATR_NUMBER] == 0)) ){
					messageFlag = true;
					strNumber = 1;
					if( g_szMessageSystem[1].equals("") ) strMessage[strNumber] = "����ȏ�A�A�C�e�������Ă܂���B";
					else strMessage[strNumber] = g_szMessageSystem[1];
				} else {
					if( statusGold >= objectAttribute[mdataBuff][ATR_GOLD] ){
						statusGold -= objectAttribute[mdataBuff][ATR_GOLD];
						if( objectAttribute[mdataBuff][ATR_ENERGY] > 30000 ){
							statusEnergy -= (objectAttribute[mdataBuff][ATR_ENERGY] -30000);
							if( (statusEnergy <= 0) && (objectAttribute[mdataBuff][ATR_ENERGY] != 0) ){
								GameOver( g );
								yesnoNumber = YESNO_NONE;
								yesnoJudge = YESNO_NONE;
								return false;
							}
						} else {
							statusEnergy += objectAttribute[mdataBuff][ATR_ENERGY];
						}
						statusStrength += objectAttribute[mdataBuff][ATR_STRENGTH];
						statusDefence += objectAttribute[mdataBuff][ATR_DEFENCE];
						
						if( objectAttribute[mdataBuff][ATR_ENERGY] != 0 ) g_iChangeStatus[0] = 20;
						if( objectAttribute[mdataBuff][ATR_STRENGTH] != 0 ) g_iChangeStatus[1] = 20;
						if( objectAttribute[mdataBuff][ATR_DEFENCE] != 0 ) g_iChangeStatus[2] = 20;
						if( objectAttribute[mdataBuff][ATR_GOLD] != 0 ) g_iChangeStatus[3] = 20;
						if( objectAttribute[objectAttribute[mdataBuff][ATR_ITEM]][ATR_STRENGTH] != 0 ) g_iChangeStatus[1] = 20;
						if( objectAttribute[objectAttribute[mdataBuff][ATR_ITEM]][ATR_DEFENCE] != 0 ) g_iChangeStatus[2] = 20;
					
						//�A�C�e���A�j���p
						if( item != 0 ) AnmItemSub( i, iBoxPos, XpBuff, YpBuff );
						//�A�C�e������
						arrangeItem( objectAttribute[mdataBuff][ATR_ITEM] );
						displayConfigWindow( g, true, true, true, false );
						//�L�����N�^�o��
						mapFlagAll = true;
						appearChara( g, XpBuff, YpBuff, 0, mdataBuff );
					} else {
						if( strMessage[6].equals("") ){
							messageFlag = true;
							strNumber = 1;
							strMessage[strNumber] = "������������Ȃ��B";
						} else if( !strMessage[6].equals("BLANK") ){
							messageFlag = true;
							strNumber = 6;
						}
					}
				}
			}
		}
		//���𔃂��L�����N�^
		if( yesnoNumber == YESNO_BUY ){
			yesnoJudgeSub( gBuff, g, strMessage[strNumber], 50, Ytop, 40*7 +16 );
			
			if( yesnoJudge == YESNO_YES ){
				for( i = 0 ; i < 12 ; ++i ){
					if( objectAttribute[mdataBuff][ATR_ITEM] == itemBox[i] ){
						statusGold += objectAttribute[mdataBuff][ATR_GOLD];
						if( objectAttribute[mdataBuff][ATR_GOLD] != 0 ) g_iChangeStatus[3] = 20;
						itemBox[i] = 0;
						arrangeItem( 0 );
						displayConfigWindow( g, true, true, true, false );
						//�L�����N�^�o��
						//mapFlagErase = true;
						mapFlagAll = true;
						appearChara( g, XpBuff, YpBuff, 0, mdataBuff );
						break;
					}
				}
				if( i == 12 ){
					if( strMessage[7].equals("") ){
						messageFlag = true;
						strNumber = 1;
						strMessage[strNumber] = "�A�C�e���������Ă��Ȃ��B";
					} else if( !strMessage[7].equals("BLANK") ){
						messageFlag = true;
						strNumber = 7;
					}
				}
			}
		}
		//��ґ���
		if( yesnoNumber == YESNO_SELECT ){
			yesnoJudgeSub( gBuff, g, strMessage[strNumber], 50, Ytop, 40*7 +16 );
			
			if( yesnoJudge == YESNO_YES ){
				//�L�����N�^�o��
				mapFlagAll = true;
				appearChara( g, XpBuff, YpBuff, 3, mdataBuff );
			} else if( yesnoJudge == YESNO_NO ){
				//�L�����N�^�o��
				mapFlagAll = true;
				appearChara( g, XpBuff, YpBuff, 4, mdataBuff );
			}
		}
		//���ʉ��f�[�^�̃��[�h
		if( yesnoNumber == YESNO_SOUND ){
			int iMesY = 360;
			if( g_szMessageSystem[2].equals("") ){
				yesnoJudgeSub( gBuff, g, "���ʉ��f�[�^�����[�h���܂����H", 50, 0, 40*7 +16 );
			} else if( g_szMessageSystem[2].equals("ON") || g_szMessageSystem[2].equals("on") ){
				yesnoJudge = YESNO_YES;
				iMesY = 230;
				DisplayMessage( "�Q�[�����J�n���܂��B\n��ʂ��N���b�N���Ă��������B", true );
			} else if( (g_szMessageSystem[2].equals("OFF")) || (g_szMessageSystem[2].equals("off")) ){
				yesnoJudge = YESNO_NO;
				DisplayMessage( "�Q�[�����J�n���܂��B\n��ʂ��N���b�N���Ă��������B", true );
			} else {
				yesnoJudgeSub( gBuff, g, g_szMessageSystem[2], 50, 0, 40*7 +16 );
			}
			yesnoFlag = false;
			
			if( yesnoJudge == YESNO_YES ){
				//�ǂݍ��݃��b�Z�[�W
				g.setColor(Color.black);
				Font fon = new Font( "TimesRoman", Font.PLAIN, 22 );
				g.setFont( fon );
				g.drawString( "Now Sound data Loading ....." ,50,iMesY );
				//�T�E���h�f�[�^$$
				for( number = 1 ; number <= 3 ; ++number ){
					if( (number == 2) ) continue;
					getAudioClipSub( number );
				}
				for( i = 0 ; i < g_iMapPartsMax ; ++i ){
					number = mapAttribute[i][ATR_SOUND];
					if( (number != 0) && (number < 100) && (audio[number] == null) ){
						getAudioClipSub( number );
					}
				}
				for( i = 0 ; i < g_iObjectPartsMax ; ++i ){
					number = objectAttribute[i][ATR_SOUND];
					if( (number != 0) && (number < 100) && (audio[number] == null) && (objectAttribute[i][ATR_TYPE] != OBJECT_RANDOM) ){
						getAudioClipSub( number );
					}
				}
				soundFlag = true;
				g.drawString( "Now Sound data Loading ..... Complete!" ,50,iMesY );
				twait( 500 );
			} else if( yesnoJudge == YESNO_NO ){
				soundFlag = false;
			}
		}
		//�����g��
		if( yesnoNumber == YESNO_USEITEM ){
			if( (charaX %5 == 0) && (charaY %5 == 0) ){
				if( strMessage[8].equals("") ) yesnoJudgeSub( gBuff, g, "���̃A�C�e�����g���܂��B\n��낵���ł����H", 50, Ytop, 40*7 +16 );
				else yesnoJudgeSub( gBuff, g, strMessage[8], 50, Ytop, 40*7 +16 );
			} else {
				//�ړ����Ȃ珈���X�L�b�v
				yesnoNumber = YESNO_NONE;
				yesnoJudge = YESNO_NONE;
				yesnoFlag = false;
			}
			if( yesnoJudge == YESNO_YES ){
				UseItem( g );
			}
		}
		//�f�[�^�̈ꎞ�ۑ�
		if( yesnoNumber == YESNO_QSAVE ){
			if( g_bSaveStop == 1 ){
				SaveStopMes();
				return true;
			}
			yesnoJudgeSub( gBuff, g, "�f�[�^�̈ꎞ�ۑ������܂��B\n��낵���ł����H\n���m���Ńf�[�^���A�p�p�X���[�h��\n�@�\���I�����ł��܂��B", 50, 0, 40*7 +16 );
			if( yesnoJudge == YESNO_YES ){
				QuickSave();
				quickSaveFlag = true;
			} else if( yesnoJudge == YESNO_NO ){
				yesnoNumber = YESNO_TEXTSAVE;
				yesnoJudge = YESNO_NONE;
				yesnoFlag = true;
				twait( 200 );
			}
		}
		//�f�[�^�̓ǂݍ���
		if( yesnoNumber == YESNO_QLOAD ){
			yesnoJudgeSub( gBuff, g, "�f�[�^��ǂݍ��݂܂����H\n���m���Ńf�[�^���A�p�p�X���[�h��\n�@���͑I�����ł��܂��B", 50, 0, 40*7 +16 );
			if( yesnoJudge == YESNO_YES ){
				QuickLoad();
			} else if( yesnoJudge == YESNO_NO ){
				yesnoNumber = YESNO_TEXTLOAD;
				yesnoJudge = YESNO_NONE;
				yesnoFlag = true;
				twait( 200 );
			}
		}
		//�ۑ��p�e�L�X�g�̕\��
		if( yesnoNumber == YESNO_TEXTSAVE ){
			if( g_bSaveStop == 1 ){
				SaveStopMes();
				return true;
			}
			yesnoJudgeSub( gBuff, g, "�f�[�^���A�p�̃p�X���[�h��\n�\�����܂����H", 50, 0, 40*7 +16 );
			if( yesnoJudge == YESNO_YES ) PressSaveMapData();
		}
		//�ۑ��p�e�L�X�g�̓���
		if( yesnoNumber == YESNO_TEXTLOAD ){
			yesnoJudgeSub( gBuff, g, "�f�[�^���A�p�̃p�X���[�h��\n���͂��܂����H", 50, 0, 40*7 +16 );
			if( yesnoJudge == YESNO_YES ) InputLoadPassword();
		}
		//����I��
		if( yesnoJudge != YESNO_NONE ){
			yesnoNumber = YESNO_NONE;
			yesnoJudge = YESNO_NONE;
			mapFlagAll = true;
			mapFlagErase = false;
			twait( 200 );
		}
		return false;
	}
	return true;
}


public void UseItem( Graphics g )
{
	//���b�Z�[�W�\��
	messageFlag = true;
	strNumber = objectAttribute[itemBox[itemboxBuff]][ATR_STRING];
	//�L�����N�^�o��
	mapFlagAll = true;
	appearChara( g, playerX, playerY, 2 );
	if( objectAttribute[itemBox[itemboxBuff]][ATR_MODE] == 1 ) itemBox[itemboxBuff] = 0;
}



//////////////////////////////////////////////////
//�}�b�v�̑S�̏�������

public void paintMapAll( Graphics g, boolean bRepaint )
{
	//�ϐ���`
	int i, j, k;
	int x, y;

	//�S�̏��������t���O������
	mapFlagAll = false;

	//�ړ��A�j���t���O������
	for( j = 0 ; j < 13 ; ++j ){
		for( i = 0 ; i < 13 ; ++i ){
			for( k = 0 ; k < 2 ; ++k ){
				g_iMapObjMove[j][i][k] = 0;
			}
		}
	}
	//��ʃt�F�[�h����
	if( g_bFadeBlack == true ){
		g_bFadeBlack = false;
		g.setColor( Color.gray );
		for( k = 0 ; k < 40*5+20 ; ++k ){
			g.drawRect( k, k, 40 *11 -k*2, 40*11 -k*2 );
			if( k %10 == 0 ) twait( 20 );
		}
		twait( 20 );
		g.fillRect( 0, 0, 440, 440 );
		mapFlagErase = true;
	}
	//���̃}�b�v��\��
	if( (mapFlagErase == true) && (bRepaint == true) ){
		mapFlagErase = false;
		SetDirectPlayer( 0 );
		//�X�N���[���\��
		if( (g_bPaintMapMove == true) && (g_bMapFlagMove == true) ){
			g_bMapFlagMove = false;
			x = 0;
			y = 0;
			for( k = 0 ; k < 11 ; ++k ){
				if( moveDirect == 4 ) x = 10 -k;
				else if( moveDirect == 6 ) x = k -10;
				else if( moveDirect == 2 ) y = k -10;
				else if( moveDirect == 8 ) y = 10 -k;
				paintMapMove( g, bRepaint, x, y );
				twait( 20 );
			}
		}
		//�����낵�\��
		else {
			for( j = 0 ; j < 11 ; ++j ){
				for( i = 0 ; i < 11 ; ++i ){
					displayCharacter( g, (i +mapX *10), (j +mapY *10) );
				}
				twait( 20 );
			}
		}
	}
	//��ʕ`��
	paintMapMove( g, bRepaint, 0, 0 );
	//��`�E�B���h�E�̕`��
	displayConfigWindow( g, true, true, true, true );
}



//////////////////////////////////////////////////
//�ړ��p�[�c����̎��̉�ʑS�̕`��

public void paintMapMove( Graphics g, boolean bRepaint, int iXtop, int iYtop )
{
	//�ϐ���`
	int i, j, k;
	int x, y;
	int iMapData;
	int mx, my;
	int iCount;
	int iCrop = 0;

	if( g_bSkipPaint == true ) return;

	//�w�i�F�œh��Ԃ�
	gBuff.setColor( Color.gray );
	gBuff.fillRect( 0, 0, 440, 440 );

	//�w�i�`��
	for( j = 0 ; j < 11 ; ++j ){
		for( i = 0 ; i < 11 ; ++i ){
			iMapData = map[j +mapY *10 +iYtop][i +mapX *10 +iXtop];
			cropID = mapAttribute[iMapData][ATR_CROP1];
			gBuff.drawImage( imgCrop[cropID], i*40, j*40, this );
			
		}
	}
	//�v���[���[�`��
	x = charaX *8;
	y = charaY *8;
	if( iXtop != 0 ){
		if( moveDirect == 4 ) x = (10 -iXtop) *40;
		else if( moveDirect == 6 ) x = (-iXtop) *40;
	}
	if( iYtop != 0 ){
		if( moveDirect == 2 ) y = (-iYtop) *40;
		else if( moveDirect == 8 ) y = (10 -iYtop) *40;
	}
	if( g_bDelPlayer == 0 ) gBuff.drawImage( imgCrop[cropIDchara], x, y, this );

	//���̕`��
	for( j = -1 ; j < 12 ; ++j ){
		for( i = -1 ; i < 12 ; ++i ){
			mx = i +mapX *10 +iXtop;
			my = j +mapY *10 +iYtop;
			if( (mx < 0) || (my < 0) || (mx >= g_iMapWidth) || (my >= g_iMapWidth) ) continue;
			iMapData = mapObject[my][mx];
			if( iMapData != 0 ){
				if( CheckNoDrawParts(iMapData, charaX /5, i, charaY /5, j) == false ){
					if( (countAnimation %44 < 22) || (objectAttribute[iMapData][ATR_CROP2] == 0) ) cropID = objectAttribute[iMapData][ATR_CROP1];
					else cropID = objectAttribute[iMapData][ATR_CROP2];
					
					if( g_iMapObjMove[j+1][i+1][0] > 0 ) --g_iMapObjMove[j+1][i+1][0];
					if( g_iMapObjMove[j+1][i+1][0] < 0 ) ++g_iMapObjMove[j+1][i+1][0];
					if( g_iMapObjMove[j+1][i+1][1] > 0 ) --g_iMapObjMove[j+1][i+1][1];
					if( g_iMapObjMove[j+1][i+1][1] < 0 ) ++g_iMapObjMove[j+1][i+1][1];
					x = i *40 +g_iMapObjMove[j+1][i+1][0] *8;
					y = j *40 +g_iMapObjMove[j+1][i+1][1] *8;
					gBuff.drawImage( imgCrop[cropID], x, y, this );
				}
			}
		}
	}

	//�t���[���Ɠ�����ʕ`��
	if( (g_iEffWait > 0) && (g_bPaintMapMove == true) ){
		for( i = 3 ; i > 0 ; --i ){
			if( g_iEffCrop[i] != 0 ) break;
		}
		iCrop = (g_iEffCurrent /g_iEffWait) %(i+1);
		++g_iEffCurrent;
	}
	for( j = 0 ; j < 11 ; ++j ){
		for( i = 0 ; i < 11 ; ++i ){
			//�����ʌ���
			if( (g_iEffWait > 0) && (g_bPaintMapMove == true) ) gBuff.drawImage( imgCrop[g_iEffCrop[iCrop]], i*40, j*40, this );
			//�t���[���`��
			paintFrame2( gBuff, i, j, i*40, j*40 );
		}
	}
	//�A�C�e���A�j���`��
	if( (g_iAnmItemCount > 0) && (g_bPaintMapMove == true) ){
		if( g_iPageNumber <= 0 ) ++g_iAnmItemCount;
		if( g_iAnmItemCount < 5 ){
			iCount = 0;
		} else {
			iCount = g_iAnmItemCount -5;
			if( iCount >= (10 +g_iAnmItemCountAdd) ) g_iAnmItemCount = 0;
		}
		x = g_iAnmItemX +(440 +(g_iAnmItemBox %3) *40 -g_iAnmItemX) *iCount /(10 +g_iAnmItemCountAdd);
		y = g_iAnmItemY +(140 +(g_iAnmItemBox /3) *40 -g_iAnmItemY) *iCount /(10 +g_iAnmItemCountAdd);
		displayConfigWindow( g, true, true, true, true );
		if( itemBox[g_iAnmItemBox] != 0 ){
			g.drawImage( imgCrop[objectAttribute[itemBox[g_iAnmItemBox]][ATR_CROP1]], x, y, this );
			gBuff.drawImage( imgCrop[objectAttribute[itemBox[g_iAnmItemBox]][ATR_CROP1]], x, y, this );
		}
	}
	//�A�����b�Z�[�W�\�����Ȃ�X�L�b�v
	if( (bRepaint == true) && (g_iPageNumber <= 0) ) g.drawImage( imgBuff, 0, 0, this );
}



//////////////////////////////////////////////////
//�v���[���[�L���������ݒ�

public void SetDirectPlayer( int iDirect )
{
	if( iDirect != 0 ) moveDirect = (char)iDirect;

	if( moveDirect == 2 ) cropIDchara = g_iImgCharaCrop +2;
	else if( moveDirect == 4 ) cropIDchara = g_iImgCharaCrop +4;
	else if( moveDirect == 6 ) cropIDchara = g_iImgCharaCrop +6;
	else cropIDchara = g_iImgCharaCrop;
}



//////////////////////////////////////////////////
//�}�b�v�ƃL�����N�^�̕`��

public void paintMap( Graphics g )
{
	//�ϐ���`
	int i, j;
	int x, y;
	int x2, y2;

	if( countRepaint == 0 ){
		//�v���[���[�L���������ݒ�
		SetDirectPlayer( 0 );
		//�L�����N�^�p�^�[���I��
		if( (((charaX %10) > 4) && ((moveDirect == 4) || (moveDirect == 6))) || (((charaY %10) > 4) && ((moveDirect == 8) || (moveDirect == 2))) ){
			++cropIDchara;
		}
	}
	//�S�`�惂�[�h
	if( g_bPaintMapMove == true ){
		paintMapMove( g, true, 0, 0 );
		return;
	}

	//���E�ւ̈ړ��̏ꍇ�B
	if( (moveDirect == 4) || (moveDirect == 6) ){
		gBuffCharaX.setColor( Color.gray );
		gBuffCharaX.fillRect( 0, 0, 80, 40 );
		
		x = charaX /5;
		y = charaY /5;
		if( (charaX %5 == 0) && (moveDirect == 6) ) --x;
		if( (charaX == 5*10) && (moveDirect == 4) ) --x;
		x2 = x;
		y2 = y;
		//�w�i�}�b�v�`��
		if( x >= 0 ){
			mdata = map[y +mapY *10][x +mapX *10];
			cropID = mapAttribute[mdata][ATR_CROP1];
			gBuffCharaX.drawImage( imgCrop[cropID], 0, 0, this );
		}
		++x;
		if( (x +mapX *10) < g_iMapWidth ){
			mdata = map[y +mapY *10][x +mapX *10];
			cropID = mapAttribute[mdata][ATR_CROP1];
			gBuffCharaX.drawImage( imgCrop[cropID], 40, 0, this );
		}
		x = (charaX %5) *8;
		if( (charaX %5 == 0) && (moveDirect == 6) ) x += 40;
		if( (charaX == 5*10) && (moveDirect == 4) ) x += 40;
		y = (charaY %5) *8;
		if( g_bDelPlayer == 0 ) gBuffCharaX.drawImage( imgCrop[cropIDchara], x, y, this );
		
		//�I�u�W�F�N�g�`��
		x = x2;
		y = y2;
		if( x >= 0 ){
			mdata = mapObject[y +mapY *10][x +mapX *10];
			if( mdata != 0 ){
				if( (countAnimation %44 < 22) || (objectAttribute[mdata][ATR_CROP2] == 0) ) cropID = objectAttribute[mdata][ATR_CROP1];
				else cropID = objectAttribute[mdata][ATR_CROP2];
				
				if( CheckNoDrawParts(mdata,x,charaX /5,y,charaY /5) == false ){
					gBuffCharaX.drawImage( imgCrop[cropID], 0, 0, this );
				}
			}
		}
		++x;
		if( (x +mapX *10) < g_iMapWidth ){
			mdata = mapObject[y +mapY *10][x +mapX *10];
			if( mdata != 0 ){
				if( (countAnimation %44 < 22) || (objectAttribute[mdata][ATR_CROP2] == 0) ) cropID = objectAttribute[mdata][ATR_CROP1];
				else cropID = objectAttribute[mdata][ATR_CROP2];
				
				if( CheckNoDrawParts(mdata,x,charaX /5,y,charaY /5) == false ){
					gBuffCharaX.drawImage( imgCrop[cropID], 40, 0, this );
				}
			}
			
		}
		//�t���[���`��
		if( x2 >= 0 ) paintFrame2( gBuffCharaX, x2, y2, 0, 0 );
		paintFrame2( gBuffCharaX, x2+1, y2, 40, 0 );
		
		x = (charaX /5) *40;
		if( (charaX %5 == 0) && (moveDirect == 6) ) x -= 40;
		if( (charaX == 5*10) && (moveDirect == 4)) x -= 40;
		y = (charaY /5) *40;
		g.drawImage( imgBuffCharaX, x, y, this );
	}
	//�㉺�ւ̈ړ��̏ꍇ�B
	if( (moveDirect == 8) || (moveDirect == 2) ){
		gBuffCharaY.setColor( Color.gray );
		gBuffCharaY.fillRect( 0, 0, 40, 80 );
		
		x = charaX /5;
		y = charaY /5;
		if( (charaY %5 == 0) && (moveDirect == 2) ) --y;
		x2 = x;
		y2 = y;
		//�w�i�}�b�v�`��
		if( y >= 0 ){
			mdata = map[y +mapY *10][x +mapX *10];
			cropID = mapAttribute[mdata][ATR_CROP1];
			gBuffCharaY.drawImage( imgCrop[cropID], 0, 0, this );
		}
		++y;
		if( (y +mapY *10) < g_iMapWidth ){
			mdata = map[y +mapY *10][x +mapX *10];
			cropID = mapAttribute[mdata][ATR_CROP1];
			gBuffCharaY.drawImage( imgCrop[cropID], 0, 40, this );
		}
		x = (charaX %5) *8;
		y = (charaY %5) *8;
		if( (charaY %5 == 0) && (moveDirect == 2) ) y += 40;
		if( g_bDelPlayer == 0 ) gBuffCharaY.drawImage( imgCrop[cropIDchara], x, y, this );
		
		//�I�u�W�F�N�g�`��
		x = x2;
		y = y2;
		if( y >= 0 ){
			mdata = mapObject[y +mapY *10][x +mapX *10];
			if( mdata != 0 ){
				if( (countAnimation %44 < 22) || (objectAttribute[mdata][ATR_CROP2] == 0) ) cropID = objectAttribute[mdata][ATR_CROP1];
				else cropID = objectAttribute[mdata][ATR_CROP2];
				
				if( CheckNoDrawParts(mdata,x,charaX /5,y,charaY /5) == false ){
					gBuffCharaY.drawImage( imgCrop[cropID], 0, 0, this );
				}
			}
		}
		++y;
		if( (y +mapY *10) < g_iMapWidth ){
			mdata = mapObject[y +mapY *10][x +mapX *10];
			if( mdata != 0 ){
				if( (countAnimation %44 < 22) || (objectAttribute[mdata][ATR_CROP2] == 0) ) cropID = objectAttribute[mdata][ATR_CROP1];
				else cropID = objectAttribute[mdata][ATR_CROP2];
				
				if( CheckNoDrawParts(mdata,x,charaX /5,y,charaY /5) == false ){
					gBuffCharaY.drawImage( imgCrop[cropID], 0, 40, this );
				}
			}
		}
		//�t���[���`��
		if( x2 >= 0 ) paintFrame2( gBuffCharaY, x2, y2, 0, 0 );
		paintFrame2( gBuffCharaY, x2, y2+1, 0, 40 );
		
		x = (charaX /5) *40;
		y = (charaY /5) *40;
		if( (charaY %5 == 0) && (moveDirect == 2) ) y -= 40;
		g.drawImage( imgBuffCharaY, x, y, this );
	}

	//�A�����b�Z�[�W�\����
	if( g_iPageNumber > 0 ) return;

	//�}�b�v�L�����N�^�`��
	for( j = 0 ; j < 11 ; ++j ){
		for( i = 0 ; i < 11 ; ++i ){
			if( (mapFlag[i][j] == true) && !((charaX /5 == i) && (charaY /5 == j)) ){
				//�v���[���[�L�����W�h�b�g�؂�΍�
				if( ((charaX /5 +1 == i) && (charaY /5 == j) && (moveDirect == 6)) || ((charaY /5 +1 == j) && (charaX /5 == i) && (moveDirect == 2)) ){
					if( (charaX %5 != 0) || (charaY %5 != 0) ) continue;
				}
				gBuffMap.setColor( Color.gray );
				gBuffMap.fillRect( 0, 0, 40, 40 );
				
				//�w�i�`��
				mdata = map[j +mapY *10 ][i +mapX *10];
				cropID = mapAttribute[mdata][ATR_CROP1];
				gBuffMap.drawImage( imgCrop[cropID], 0, 0, this );
				
				//�I�u�W�F�N�g�`��
				mdata = mapObject[j +mapY *10][i +mapX *10];
				if( (mdata != 0) && (CheckNoDrawParts(mdata,charaX /5,i,charaY /5,j) == false) ){
					if( (countAnimation %44 < 22) || (objectAttribute[mdata][ATR_CROP2] == 0) ){
						cropID = objectAttribute[mdata][ATR_CROP1];
					} else {
						cropID = objectAttribute[mdata][ATR_CROP2];
					}
					gBuffMap.drawImage( imgCrop[cropID], 0, 0, this );
				}
				//�t���[���`��
				paintFrame2( gBuffMap, i, j, 0, 0 );
				
				g.drawImage( imgBuffMap, i*40, j*40, this );
			}
		}
	}
}



//////////////////////////////////////////////////
//�}�b�v���������p�̃t���O�𗧂Ă�

public void setMapFlag()
{
	//�ϐ���`
	int i, j;

	//�t���O�𗧂Ă�
	for( j = 0 ; j < 11 ; ++j ){
		for( i = 0 ; i < 11 ; ++i ){
			mapFlag[i][j] = false;
			mdata = mapObject[j +mapY *10][i +mapX *10];
			if( (mdata != 0) && (countAnimation %11 == i) && (objectAttribute[mdata][ATR_CROP2] != 0) ){
				mapFlag[i][j] = true;
			}
		}
	}
	++countAnimation;
}



//////////////////////////////////////////////////
//�g�̕`��

public void paintFrame2( Graphics g, int x, int y, int x2, int y2 )
{
	//�t���[���`��
	cropID = 0;

	if( (x == 0) && (y == 0) ) cropID = CROP_MAINFRAME;
	else if( (x == 10) && (y == 0) ) cropID = CROP_MAINFRAME +2;
	else if( (x == 0) && (y == 10) ) cropID = CROP_MAINFRAME +20;
	else if( (x == 10) && (y == 10) ) cropID = CROP_MAINFRAME +22;
	else if( y == 0 ) cropID = CROP_MAINFRAME +1;
	else if( x == 0 ) cropID = CROP_MAINFRAME +10;
	else if( x == 10 ) cropID = CROP_MAINFRAME +12;
	else if( y == 10 ) cropID = CROP_MAINFRAME +21;

	if( cropID != 0 ) g.drawImage( imgCrop[cropID], x2, y2, this );
}



//////////////////////////////////////////////////
//�L�[�ƃ}�E�X�̋��ʏ���

public boolean KeyMouseDown( int key, int x, int y )
{
	countRepaint = 0;

	//�g���N���X�Ăяo��
	if( (attackFlag == true) || (g_bStopInput == true) ) return true;

	if( displayMonsterFlag == true ){
		displayMonsterFlag = false;
		mapFlagAll = true;
		mapFlagErase = false;
	}
	//URL�����N����̕��A
	if( urlJumpFlag == true ){
		mapFlagAll = true;
		mapFlagErase = false;
		urlJumpFlag = false;
		System.gc();	//���������
		return true;
	}
	//�O���E�B���h�����
	if( g_bOpenFrameWin == true ){
		g_bOpenFrameWin = false;
		if( g_bOpenLoadPass == true ) ExtractLoadMapData();
		g_frameWin.dispose();
	}
	return false;
}


public void KeyMouseItem( int i )
{
	if( objectAttribute[itemBox[i]][ATR_MODE] != 0 ){
		itemboxBuff = i;
		yesnoNumber = YESNO_USEITEM;
		yesnoFlag = true;
		g_iUseItem = itemBox[i];
	}
}



//////////////////////////////////////////////////
//�L�[�������ꂽ�Ƃ�

String g_szKeyList = "�@�y�V���[�g�J�b�g�L�[�̈ꗗ�z\n�e�P�A�l�F�퓬���ʗ\���̕\��\n�e�Q�A�o�F�ړ����x�̐؂芷��\n�e�R�F���A�p�p�X���[�h����\n�e�S�F���A�p�p�X���[�h�\��\n�e�T�F�ꎞ�ۑ��f�[�^�̓ǂݍ���\n�e�U�F�f�[�^�̈ꎞ�ۑ�\n�e�V�F���߂���X�^�[�g\n�e�W�F�v�v�`�����y�[�W�Ƀ����N\n�e�X�A�f�F�`�惂�[�h�̐؂芷��\n�e�P�Q�F���̃��X�g�̕\��\n�k�F�����N��ʂ̃E�B���h�E�ŊJ��\n�L�[�{�[�h�́u�P�Q�R�A�p�v�d�A\n�`�r�c�A�y�w�b�v��\n�E�̃A�C�e���{�b�N�X�ɑΉ��B\n�u�d���������A�x�v�͂x����\n�u�d�����A�m�v�͂m���ɑΉ��B\n�@�@���݂̈ړ��񐔁F";

public boolean keyDown( Event e, int key )
{
	//�L�[�ƃ}�E�X�̋��ʏ���
	if( KeyMouseDown( key, 0, 0 ) == true ) return true;

	//�x�d�r�C�m�n�̔���
	if( yesnoNumber != YESNO_NONE ){
		if( (key == 10) || (key == 13) || (key == 'y') || (key == 'Y') ) yesnoJudge = YESNO_YES;
		if( (key == 27) || (key == 'n') || (key == 'N') ) yesnoJudge = YESNO_NO;
		return true;
	}
	//�G���^�[�L�[�܂��̓X�y�[�X�L�[�C�d�r�b�L�[
	if( ((key == 10) || (key == 13) || (key == 32) || (key == 27)) && (inputKey == false) ){
		//if( (soundFlag == true) && (inputKey == false) && (yesnoFlag == false) ) playAudio( 2 );
		inputKey = true;
		return true;
	}
	//�A�����b�Z�[�W�\�����͂��̑��̏������L�����Z��
	if( g_iPageNumber > 0 ) return true;

	//�����X�^�[�̔\�͒l�\��
	if( ((key == Event.F1) || (key == 'm' || key == 'M')) && (inputKey == true) && (yesnoNumber == YESNO_NONE) ){
		displayMonsterFlag = true;
		inputKey = false;
	}

	//�z�[���y�[�W�ֈړ��̏ꍇ
	if( key == Event.F8 ){
		yesnoNumber = YESNO_WWAH;
		yesnoFlag = true;
	}
	//���X�^�[�g�̏ꍇ
	if( key == Event.F7 ){
		yesnoNumber = YESNO_RESTART;
		yesnoFlag = true;
	}
	//�N�C�b�N�Z�[�u�̏ꍇ
	if( key == Event.F6 ){
		yesnoNumber = YESNO_QSAVE;
		yesnoFlag = true;
	}
	if( key == Event.F5 ){
		if( quickSaveFlag == true ){
			yesnoNumber = YESNO_QLOAD;
		} else {
			yesnoNumber = YESNO_TEXTLOAD;
		}
		yesnoFlag = true;
	}
	//�p�X���[�h�e�L�X�g
	if( key == Event.F3 ){
		yesnoNumber = YESNO_TEXTLOAD;
		yesnoFlag = true;
	}
	if( key == Event.F4 ){
		yesnoNumber = YESNO_TEXTSAVE;
		yesnoFlag = true;
	}
	//�Ή��\�\��
	if( key == Event.F12 ){
		DisplayMessage( g_szKeyList +g_iStep, true );
	}

	//�`�惂�[�h
	if( ((key == Event.F9) || (key == 'g' || key == 'G')) ){
		if( g_bPaintMapMove == true ){
			DisplayMessage( "�����`��i������ʕ`��j���[�h��\n�؂芷���܂��B\n���삪�d����X�y�b�N�̃}�V����\n�g�p���Ă��������B", true );
			g_bPaintMapMove = false;
		} else {
			DisplayMessage( "�ʏ�`��i�S��ʕ`��j���[�h��\n�؂芷���܂��B", true );
			g_bPaintMapMove = true;
		}
		mapFlagAll = true;
	}
	//���x�؂芷��
	if( ((key == Event.F2) || (key == 'p' || key == 'P')) ){
		if( TimerCount == 20 ){
			DisplayMessage( "�ړ����x�������ɐ؂芷���܂��B\n�퓬�������Ȃ�܂��B", true );
			TimerCount = 10;
		} else {
			DisplayMessage( "�ړ����x��ʏ�ɐ؂芷���܂��B", true );
			TimerCount = 20;
		}
	}
	if( (key == 'i' || key == 'I') ){
		if( TimerCount != 60 ){
			DisplayMessage( "�ړ����x��ᑬ�ɐ؂芷���܂��B", true );
			TimerCount = 60;
		} else {
			DisplayMessage( "�ړ����x��ʏ�ɐ؂芷���܂��B", true );
			TimerCount = 20;
		}
	}
	//�����N���[�h�؂芷��
	if( (key == 'l' || key == 'L') ){
		if( g_bPopup == false ){
			DisplayMessage( "�����N��ʂ̃E�B���h�E��\n�J���悤�ɂ��܂��B", true );
			g_bPopup = true;
		} else if( g_bPopup == true ){
			DisplayMessage( "�����N�𓯂��E�B���h�E��\n�J���悤�ɂ��܂��B", true );
			g_bPopup = false;
		}
	}

	//if( (key == '8') || (key == Event.UP) ){
	if( key == Event.UP ){
		UpKey = true;
		currentKey = 8;
	}
	//else if( key == '2' || (key == Event.DOWN) ){
	else if( key == Event.DOWN ){
		DownKey = true;
		currentKey = 2;
	}
	//else if( key == '4' || (key == Event.LEFT) ){
	else if( key == Event.LEFT ){
		LeftKey = true;
		currentKey = 4;
	}
	//else if( key == '6' || (key == Event.RIGHT) ){
	else if( key == Event.RIGHT ){
		RightKey = true;
		currentKey = 6;
	}
	else if( key == Event.HOME ){
		mapFlagAll = true;
		mapFlagErase = true;
	}

	//�A�C�e���{�b�N�X�̏ꍇ
	int i;
	char cItemKey[] = { '1','!', '2','"', '3','#', 'q','Q', 'w','W', 'e','E', 'a','A', 's','S', 'd','D', 'z','Z', 'x','X', 'c','C' };
	for( i = 0 ; i < 12 ; ++i ){
		if( (key == cItemKey[i*2]) || (key == cItemKey[i*2+1]) ) KeyMouseItem( i );
	}
	//���ʉ�
	if( (yesnoFlag == true) || (displayMonsterFlag == true) ) playAudio( 1 );

	return true;
}



//////////////////////////////////////////////////
//�L�[�������ꂽ�Ƃ�

public boolean keyUp( Event e, int key )
{
	//�J�[�\���L�[�̏ꍇ
	if( key == Event.UP ) UpKey = false;
	else if( key == Event.DOWN ) DownKey = false;
	else if( key == Event.LEFT ) LeftKey = false;
	else if( key == Event.RIGHT ) RightKey = false;
	//�e���L�[�̏ꍇ
	else {
		UpKey = false;
		DownKey = false;
		LeftKey = false;
		RightKey = false;
	}
	return true;
}



//////////////////////////////////////////////////
//�}�E�X�������ꂽ�Ƃ�

public boolean mouseDown( Event evt, int x, int y )
{
	boolean bSound = false;

	//�L�[�ƃ}�E�X�̋��ʏ���
	if( KeyMouseDown( 0, x, y ) == true ) return true;

	if( inputKey == false ){
		inputKey = true;
		return true;
	}

	//�x�d�r�C�m�n�̔���
	if( yesnoNumber != YESNO_NONE ){
		if( (x > yesnoX) && (x < yesnoX +40) && (y > yesnoY) && (y < yesnoY +40) ) yesnoJudge = YESNO_YES;
		if( (x > yesnoX+40) && (x < yesnoX+80) && (y > yesnoY) && (y < yesnoY+40) ) yesnoJudge = YESNO_NO;
		return true;
	}
	//�A�����b�Z�[�W�\�����͂��̑��̏������L�����Z��
	if( g_iPageNumber > 0 ) return true;

	//�Ή��\�\��
	if( (x > 40*11) && (x < 40*14) && (y > 0) && (y < 35*4) && (yesnoNumber == YESNO_NONE) ){
		DisplayMessage( g_szKeyList +g_iStep, true );
	}

	//�ړ��̏ꍇ
	int stanceX;
	int stanceY;
	if( (x > 0) && (x < 40*11) && (y > 0) && (y < 40*11) ){
		if( y >= charaY *8 +20 ){
			stanceY = y -(charaY *8 +20);
		} else {
			stanceY = (charaY *8 +20) -y;
		}
		if( x >= charaX *8 +20 ){
			stanceX = x -(charaX *8 +20);
		} else {
			stanceX = (charaX *8 +20) -x;
		}
		//�������ֈړ�
		if( (charaY *8 +20 < y) && (stanceY > stanceX) ){
			DownKey = true;
		}
		//������ֈړ�
		else if( (charaY *8 +20 > y) && (stanceY > stanceX) ){
			UpKey = true;
		}
		//�E�����ֈړ�
		else if( (charaX *8 +20 < x) && (stanceY < stanceX) ){
			RightKey = true;
		}
		//�������ֈړ�
		else if( (charaX *8 + 20 > x) && (stanceY < stanceX) ){
			LeftKey = true;
		}
	}

	//�z�[���y�[�W�ֈړ��̏ꍇ
	if( (x > 40*11) && (x < 40*14) && (y > 405) && (y < 440) ){
		yesnoNumber = YESNO_WWAH;
		yesnoFlag = true;
	}
	//���X�^�[�g�̏ꍇ
	if( (x > 40*11) && (x < 40*14) && (y > 370) && (y < 405) ){
		yesnoNumber = YESNO_RESTART;
		yesnoFlag = true;
	}
	//�N�C�b�N�Z�[�u�̏ꍇ
	if( (x > 40*11) && (x < 40*14) && (y > 335) && (y < 370) ){
		yesnoNumber = YESNO_QSAVE;
		yesnoFlag = true;
	}
	if( (x > 40*11) && (x < 40*14) && (y > 300) && (y < 335) ){
		if( quickSaveFlag == true ){
			yesnoNumber = YESNO_QLOAD;
		} else {
			yesnoNumber = YESNO_TEXTLOAD;
		}
		yesnoFlag = true;
	}
	//�A�C�e���{�b�N�X�̏ꍇ
	int i;
	for( i = 0 ; i < 12 ; ++i ){
		if( (x > 40*(11+i%3)) && (x < 40*(12+i%3)) && (y > 140+40*(i/3)) && (y < 180+40*(i/3)) ){
			KeyMouseItem( i );
		}
	}

	//���ʉ�
	if( (yesnoFlag == true) || (displayMonsterFlag == true) ) playAudio( 1 );

	return true;
}



//////////////////////////////////////////////////
//�}�E�X�������ꂽ�Ƃ�

public boolean mouseUp( Event evt, int x, int y )
{
	UpKey = false;
	DownKey = false;
	LeftKey = false;
	RightKey = false;
	
	return true;
}



//////////////////////////////////////////////////
//�ړ������̕��̃`�F�b�N

public boolean CheckNoDrawParts( int iMapData, int x, int x2, int y, int y2 )
{
	if( (charaX %5 != 0) || (charaY %5 != 0) ) return false;

	if( (x == x2) && (y == y2) && (g_bDefault == 0) ){
		if( ((objectAttribute[iMapData][ATR_NUMBER] == 0) && (objectAttribute[iMapData][ATR_TYPE] == OBJECT_DOOR))
			|| (objectAttribute[iMapData][ATR_TYPE] == OBJECT_STATUS) || (objectAttribute[iMapData][ATR_TYPE] == OBJECT_MESSAGE)
				|| (objectAttribute[iMapData][ATR_TYPE] == OBJECT_ITEM) || (objectAttribute[iMapData][ATR_TYPE] == OBJECT_SELL)
					|| (objectAttribute[iMapData][ATR_TYPE] == OBJECT_BUY) || (objectAttribute[iMapData][ATR_TYPE] == OBJECT_SELECT) || (objectAttribute[iMapData][ATR_TYPE] == OBJECT_LOCALGATE) ){
			return true;
		}
	}
	return false;
}


public void AnmItemSub( int i, int iBoxPos, int Xpoint, int Ypoint )
{
	//�A�C�e���A�j���p
	if( (g_bAnmItem == 1) && (g_bPaintMapMove == true) && (g_iAnmItemCount == 0) ){
		g_iAnmItemBox = i;
		if( iBoxPos != 0 ) g_iAnmItemBox = iBoxPos -1;
		g_iAnmItemOld = itemBox[g_iAnmItemBox];
		g_iAnmItemCount = 1;
		g_iAnmItemX = (Xpoint -mapX *10) *40;
		g_iAnmItemY = (Ypoint -mapY *10) *40;
		g_iAnmItemCountAdd = 10 -(Xpoint -mapX *10);
	}
}


public boolean directCheck( Graphics g, int Xpoint, int Ypoint )
{
	//�ϐ�
	int i, j;
	int x, y;
	int number;
	boolean flag = true;
	int iMapNumber;
	int jumpX, jumpY;
	int iBoxPos;

	g_iXpoint = Xpoint;
	g_iYpoint = Ypoint;

	//�}�b�v�`�F�b�N
	mdata = map[Ypoint][Xpoint];
	if( (mdata == 0) && (g_bOldMap == 0) ) flag = false;

	if( mapAttribute[mdata][ATR_TYPE] == MAP_WALL ){
		number = mapAttribute[mdata][ATR_STRING];
		if( number != 0 ){
			strNumber = number;
			messageFlag = true;
		}
		//�L�����N�^�o��
		appearChara( g, Xpoint, Ypoint, 1 );
		//�T�E���h
		if( mapAttribute[mdata][ATR_SOUND] != 0 ) playAudio( mapAttribute[mdata][ATR_SOUND] );
		//�i�s�s��
		flag = false;
	}

	//�I�u�W�F�N�g�`�F�b�N
	mdata = mapObject[Ypoint][Xpoint];
	iMapNumber = mdata;
	if( mdata != 0 ){
		flag = false;
		//����
		if( objectAttribute[mdata][ATR_TYPE] == OBJECT_NORMAL ){
			if( (objectAttribute[mdata][ATR_MODE] == 1) && (mapAttribute[map[Ypoint][Xpoint]][ATR_TYPE] != MAP_WALL) ) flag = true;
		}
		//�t�q�k�Q�[�g
		else if( objectAttribute[mdata][ATR_TYPE] == OBJECT_URLGATE ){
			messageFlag = false;
			strNumber = objectAttribute[mdata][ATR_STRING];
			jumpURL( new String(strMessage[strNumber]), true );
		}
		//�X�e�[�^�X�A�C�e��
		else if( objectAttribute[mdata][ATR_TYPE] == OBJECT_STATUS ){
			//�}�C�i�X����
			if( ((objectAttribute[mdata][ATR_STRENGTH] > 30000) && (statusStrength < (objectAttribute[mdata][ATR_STRENGTH] -30000)))
					|| ((objectAttribute[mdata][ATR_DEFENCE] > 30000) && (statusDefence < (objectAttribute[mdata][ATR_DEFENCE] -30000)))
						|| ((objectAttribute[mdata][ATR_GOLD] > 30000) && (statusGold < (objectAttribute[mdata][ATR_GOLD] -30000))) ){
				mapObject[Ypoint][Xpoint] = 0;
				displayCharacter( g, Xpoint, Ypoint );
				return false;
			}
			//������
			if( objectAttribute[mdata][ATR_ENERGY] > 30000 ){
				statusEnergy -= (objectAttribute[mdata][ATR_ENERGY] -30000);
				if( (statusEnergy <= 0) && (objectAttribute[mdata][ATR_ENERGY] != 0) ){
					GameOver( g );
					return false;
				}
			} else {
				statusEnergy += objectAttribute[mdata][ATR_ENERGY];
			}
			//�U����
			if( objectAttribute[mdata][ATR_STRENGTH] > 30000 ){
				statusStrength -= (objectAttribute[mdata][ATR_STRENGTH] -30000);
			} else {
				statusStrength += objectAttribute[mdata][ATR_STRENGTH];
			}
			//�h���
			if( objectAttribute[mdata][ATR_DEFENCE] > 30000 ){
				statusDefence -= (objectAttribute[mdata][ATR_DEFENCE] -30000);
			} else {
				statusDefence += objectAttribute[mdata][ATR_DEFENCE];
			}
			//������
			if( objectAttribute[mdata][ATR_GOLD] > 30000 ){
				statusGold -= (objectAttribute[mdata][ATR_GOLD] -30000);
			} else {
				statusGold += objectAttribute[mdata][ATR_GOLD];
			}
			if( objectAttribute[mdata][ATR_ENERGY] != 0 ) g_iChangeStatus[0] = 20;
			if( objectAttribute[mdata][ATR_STRENGTH] != 0 ) g_iChangeStatus[1] = 20;
			if( objectAttribute[mdata][ATR_DEFENCE] != 0 ) g_iChangeStatus[2] = 20;
			if( objectAttribute[mdata][ATR_GOLD] != 0 ) g_iChangeStatus[3] = 20;
			//�X�e�[�^�X�\��
			displayConfigWindow( g, true, true, true, false );
			//���b�Z�[�W�\��
			messageFlag = true;
			strNumber = objectAttribute[mdata][ATR_STRING];
			//���̏���
			mapObject[Ypoint][Xpoint] = 0;
			//�L�����N�^�o��
			appearChara( g, Xpoint, Ypoint, 0, iMapNumber );
		}
		//�A�C�e��
		else if( objectAttribute[mdata][ATR_TYPE] == OBJECT_ITEM ){
			for( i = 0 ; i < 12 ; ++i ){
				if( itemBox[i] == 0 ) break;
			}
			//�A�C�e�����P�Q���܂��Ă���ꍇ
			iBoxPos = objectAttribute[mdata][ATR_NUMBER];
			if( (i == 12) && ((iBoxPos == 0) || (objectAttribute[itemBox[iBoxPos-1]][ATR_NUMBER] == 0)) ){
				messageFlag = true;
				strNumber = 1;
				if( g_szMessageSystem[1].equals("") ) strMessage[strNumber] = "����ȏ�A�A�C�e�������Ă܂���B";
				else strMessage[strNumber] = g_szMessageSystem[1];
			} else {
				if( objectAttribute[mdata][ATR_STRENGTH] != 0 ) g_iChangeStatus[1] = 20;
				if( objectAttribute[mdata][ATR_DEFENCE] != 0 ) g_iChangeStatus[2] = 20;
				//�A�C�e���A�j���p
				AnmItemSub( i, iBoxPos, Xpoint, Ypoint );
				//�A�C�e������
				arrangeItem( mdata );
				displayConfigWindow( g, true, true, true, false );
				//���̏���
				mapObject[Ypoint][Xpoint] = 0;
				//�g�p�^�A�C�e���̏ꍇ
				if( objectAttribute[mdata][ATR_MODE] != 0 ){
					if( bDispUseItemMes == true ){
						if( g_szMessageSystem[0].equals("") ) DisplayMessage( "���̃A�C�e���͉E�̃{�b�N�X��\n�N���b�N���邱�ƂŎg�p�ł��܂��B\n�g�p�ł���A�C�e����\n�F�g�ň͂܂�܂��B", false );
						else if( !(g_szMessageSystem[0].equals("BLANK")) ) DisplayMessage( g_szMessageSystem[0], false );
						bDispUseItemMes = false;
					}
				} else {
					//���b�Z�[�W�\��
					messageFlag = true;
					strNumber = objectAttribute[mdata][ATR_STRING];
					//�L�����N�^�o��
					appearChara( g, Xpoint, Ypoint, 0, iMapNumber );
				}
			}
		}
		//��
		else if( objectAttribute[mdata][ATR_TYPE] == OBJECT_DOOR ){
			for( i = 0 ; i < 12 ; ++i ){
				if( objectAttribute[mdata][ATR_ITEM] == itemBox[i] ){
					if( g_bNoExec == true ) return false;
					//�A�C�e������
					if( objectAttribute[mdata][ATR_MODE] == 0 ){
						itemBox[i] = 0;
						arrangeItem( 0 );
					}
					displayConfigWindow( g, true, true, true, false );
					//���b�Z�[�W�\��
					messageFlag = true;
					strNumber = objectAttribute[mdata][ATR_STRING];
					//���̏���
					mapObject[Ypoint][Xpoint] = 0;
					if( g_bPaintMapMove == true ) g_bSkipPaint = true;
					//�L�����N�^�o��
					appearChara( g, Xpoint, Ypoint, 0, iMapNumber );
					movingSkip = 1;
					if( objectAttribute[mdata][ATR_SOUND] != 0 ) playAudio( objectAttribute[mdata][ATR_SOUND] );
					break;
				}
			}
			if( (i == 12) && (objectAttribute[mdata][ATR_NUMBER] == 1) ) return true;
		}
		//�����X�^�[
		else if( objectAttribute[mdata][ATR_TYPE] == OBJECT_MONSTER ){
			attackFlag = true;
			attackFlagTurn = true;
			monsterEnergy = objectAttribute[mdata][ATR_ENERGY];
			monsterStrength = objectAttribute[mdata][ATR_STRENGTH];
			monsterDefence = objectAttribute[mdata][ATR_DEFENCE];
			monsterGold = objectAttribute[mdata][ATR_GOLD];
			attackXp = Xpoint;
			attackYp = Ypoint;
			messageFlag = false;
			g_iAttackTurn = 0;
			
			displayMonsterStatus( g, mdata );
			twait( 200 );
		}
		//���b�Z�[�W�L�����N�^
		else if( objectAttribute[mdata][ATR_TYPE] == OBJECT_MESSAGE ){
			//g_iOldPartsObject = mdata;
			//�҂�����
			if( objectAttribute[mdata][ATR_NUMBER] != 0 ) twait( objectAttribute[mdata][ATR_NUMBER] *100, g );
			strNumber = objectAttribute[mdata][ATR_STRING];
			if( strNumber != 0 ) messageFlag = true;
			//�v���[���[�Ɠ����ʒu�Ȃ�Ώ���
			if( (Xpoint == playerX) && (Ypoint == playerY) && (g_bDefault == 0) ) mapObject[Ypoint][Xpoint] = 0;
			//�L�����N�^�o��
			appearChara( g, Xpoint, Ypoint, 0, iMapNumber );
		}
		//�X�R�A�L�����N�^
		if( objectAttribute[mdata][ATR_TYPE] == OBJECT_SCORE ){
			scoreFlag = true;
			messageFlag = true;
			strNumber = objectAttribute[mdata][ATR_STRING];
			//�����񂪂Ȃ��ꍇ
			if( strNumber == 0 ){
				strNumber = 1;
				strMessage[strNumber] = "�X�R�A��\�����܂��B";
			}
			score = 0;
			score += objectAttribute[mdata][ATR_ENERGY] * statusEnergy;
			score += objectAttribute[mdata][ATR_STRENGTH] *(statusStrength +itemStrength);
			score += objectAttribute[mdata][ATR_DEFENCE] *(statusDefence +itemDefence);
			score += objectAttribute[mdata][ATR_GOLD] *statusGold;
		}
		//���𔃂��L�����N�^
		else if( objectAttribute[mdata][ATR_TYPE] == OBJECT_BUY ){
			mdataBuff = mdata;
			XpBuff = Xpoint;
			YpBuff = Ypoint;
			strNumber = objectAttribute[mdata][ATR_STRING];
			
			messageFlag = false;
			yesnoNumber = YESNO_BUY;
			yesnoFlag = true;
			
			//�v���[���[�Ɠ����ʒu�Ȃ�Ώ���
			if( (Xpoint == playerX) && (Ypoint == playerY) && (g_bDefault == 0) ) mapObject[Ypoint][Xpoint] = 0;
		}
		//���𔄂�L�����N�^
		else if( objectAttribute[mdata][ATR_TYPE] == OBJECT_SELL ){
			mdataBuff = mdata;
			XpBuff = Xpoint;
			YpBuff = Ypoint;
			strNumber = objectAttribute[mdata][ATR_STRING];
			
			messageFlag = false;
			yesnoNumber = YESNO_SELL;
			yesnoFlag = true;
			
			//�v���[���[�Ɠ����ʒu�Ȃ�Ώ���
			if( (Xpoint == playerX) && (Ypoint == playerY) && (g_bDefault == 0) ) mapObject[Ypoint][Xpoint] = 0;
		}
		//��ґ���
		else if( objectAttribute[mdata][ATR_TYPE] == OBJECT_SELECT ){
			mdataBuff = mdata;
			XpBuff = Xpoint;
			YpBuff = Ypoint;
			strNumber = objectAttribute[mdata][ATR_STRING];
			
			messageFlag = false;
			yesnoNumber = YESNO_SELECT;
			yesnoFlag = true;
			
			//�v���[���[�Ɠ����ʒu�Ȃ�Ώ���
			if( (Xpoint == playerX) && (Ypoint == playerY) && (g_bDefault == 0) ) mapObject[Ypoint][Xpoint] = 0;
		}
		//���[�J���Q�[�g�̏ꍇ
		else if( objectAttribute[mdata][ATR_TYPE] == OBJECT_LOCALGATE ){
			//�v���[���[�Ɠ����ʒu�Ȃ�Ώ���
			if( (Xpoint == playerX) && (Ypoint == playerY) && (g_bDefault == 0) ) mapObject[Ypoint][Xpoint] = 0;
			//�L�����N�^�o��
			appearChara( g, Xpoint, Ypoint, 0, iMapNumber );
			//�ʒu�ݒ�
			jumpX = objectAttribute[mdata][ATR_JUMP_X];
			jumpY = objectAttribute[mdata][ATR_JUMP_Y];
			JumpPoint( jumpX, jumpY );
			//�T�E���h
			if( objectAttribute[mdata][ATR_SOUND] != 0 ) playAudio( objectAttribute[mdata][ATR_SOUND] );
			return false;
		}
		//�T�E���h
		if( (objectAttribute[mdata][ATR_TYPE] != OBJECT_MONSTER) && (objectAttribute[mdata][ATR_TYPE] != OBJECT_DOOR) ) playAudio( objectAttribute[mdata][ATR_SOUND] );
	}

	//�i�s�����ĕ`��
	x =  Xpoint %10;
	if( (x == 0) && ((charaX /5) >= 9) ) x = 10;
	y =  Ypoint %10;
	if( (y == 0) && ((charaY /5) >= 9) ) y = 10;
	mapFlag[x][y] = true;

	return flag;
}



//////////////////////////////////////////////////
//���݈ʒu�̔w�i�p�[�c�`�F�b�N

int oldMapData;
int oldObjectData;
int oldMapX, oldMapY;

public boolean pointCheckMap( Graphics g, int mapData,int x, int y )
{
	int i;
	int number;

	g_iXpoint = playerX;
	g_iYpoint = playerY;

	//���̏ꍇ
	if( mapAttribute[mapData][ATR_TYPE] == MAP_STREET ){
		//����̃A�C�e���݂̂ɔ�������ꍇ
		if( mapAttribute[mapData][ATR_ITEM] != 0 ){
			for( i = 0 ; i < 12 ; ++i ){
				if( itemBox[i] == mapAttribute[mapData][ATR_ITEM] ) break;
			}
			if( i == 12 ) return false;
		}
		//�L�����N�^�o��
		appearChara( g, x+mapX*10, y+mapY*10, 1 );
		//�T�E���h
		if((mapAttribute[mapData][ATR_SOUND] != 0) && (soundFlag == true)) playAudio( mapAttribute[mapData][ATR_SOUND] );
		//�҂�����
		if( mapAttribute[mapData][ATR_NUMBER] != 0 ) twait( mapAttribute[mapData][ATR_NUMBER] *100, g );
		//���b�Z�[�W
		number = mapAttribute[mapData][ATR_STRING];
		if( number != 0 ){
			strNumber = number;
			messageFlag = true;
			return true;
		}
		if( mapAttribute[mapData][ATR_NUMBER] != 0 ) return true;
	}
	//���[�J���Q�[�g�̏ꍇ
	else if( mapAttribute[mapData][ATR_TYPE] == MAP_LOCALGATE ){
		//�L�����N�^�o��
		appearChara( g, playerX, playerY, 1 );
		//�ʒu�ݒ�
		int jumpX = mapAttribute[mapData][ATR_JUMP_X];
		int jumpY = mapAttribute[mapData][ATR_JUMP_Y];
		JumpPoint( jumpX, jumpY );
		//�T�E���h
		if((mapAttribute[mapData][ATR_SOUND] != 0) && (soundFlag == true)) playAudio( mapAttribute[mapData][ATR_SOUND] ); //audio[mapAttribute[mapData][ATR_SOUND]].play();
		return true;
	}
	//�t�q�k�Q�[�g
	else if( mapAttribute[mapData][ATR_TYPE] == MAP_URLGATE ){
		strNumber = mapAttribute[mapData][ATR_STRING];
		jumpURL( new String(strMessage[strNumber]), true );
		return true;
	}
	return false;
}


public boolean pointCheck( Graphics g )
{
	//int i;
	int x, y;
	//int number;
	int mapData;
	int objectData;

	//�}�b�v�`�F�b�N
	x = charaX /5;
	y = charaY /5;
	mapData = map[y+mapY*10][x+mapX*10];
	objectData = mapObject[y+mapY*10][x+mapX*10];

	//�ʒu���������`�F�b�N�i�w�i�j
	if( !((mapData == oldMapData) && (oldMapX == x+mapX*10) && (oldMapY == y+mapY*10)) ){
		oldMapX = x +mapX *10;
		oldMapY = y +mapY *10;
		oldMapData = mapData;
		//�w�i�`�F�b�N
		if( pointCheckMap(g,mapData,x,y) == true ) return true;
	}
	//�ʒu���������`�F�b�N�i���́j
	if( !((objectData == oldObjectData) && (oldMapX == x+mapX*10) && (oldMapY == y+mapY*10)) ){
		oldMapX = x +mapX *10;
		oldMapY = y +mapY *10;
		oldObjectData = objectData;
		//���݈ʒu�̕��̃`�F�b�N
		objectData = mapObject[y+mapY*10][x+mapX*10];
		if( objectAttribute[objectData][ATR_TYPE] != 0 ){
			displayCharacter( g, x+mapX*10, y+mapY*10 );
			//���̃`�F�b�N
			directCheck( g, charaX/5+mapX*10, charaY/5+mapY*10 );
			return true;
		}
	}
	return false;
}



//////////////////////////////////////////////////
//�W�����v�ݒ�

public boolean JumpPointSub( int x, int y, int iDirect )
{
	int iMapData;

	//�l���ɃQ�[�g������Ό����ύX
	if( (x >= 0) && (x <= g_iMapWidth-1) && (y >= 0) && (y <= g_iMapWidth-1) ){
		iMapData = map[y][x];
		if( mapAttribute[iMapData][ATR_TYPE] == MAP_LOCALGATE ){
			SetDirectPlayer( iDirect );
			return true;
		}
		iMapData = mapObject[y][x];
		if( objectAttribute[iMapData][ATR_TYPE] == OBJECT_LOCALGATE ){
			SetDirectPlayer( iDirect );
			return true;
		}
	}
	return false;
}


public void JumpPoint( int jumpX, int jumpY )
{
	int iMapData;

	if( jumpX > 9000 ) jumpX = playerX + jumpX - 10000;
	if( jumpY > 9000 ) jumpY = playerY + jumpY - 10000;

	if( (jumpX >= 0) && (jumpX <= g_iMapWidth-1) && (jumpY >= 0) && (jumpY <= g_iMapWidth-1) ){
		int mapXb = mapX;
		int mapYb = mapY;
		if( jumpX == g_iMapWidth-1 ){
			mapX = jumpX /10 -1;
			charaX = 10 *5;
		} else {
			mapX = jumpX /10;
			charaX = (jumpX %10) *5;
		}
		if( jumpY == g_iMapWidth-1 ){
			mapY = jumpY /10 -1;
			charaY = 10 *5;
		} else {
			mapY = jumpY /10;
			charaY = (jumpY %10) *5;
		}
		mapFlagAll = true;
		
		//�����ʂȂ珑���������Ȃ�
		if( (mapX == mapXb) && (mapY == mapYb) ){
			mapFlagErase = false;
		} else {
			mapFlagErase = true;
			g_bRestPlayer = true;
		}
		//�v���[���[�̌��݈ʒu�擾
		playerX = charaX /5 +mapX *10;
		playerY = charaY /5 +mapY *10;
		
		//�l���ɃQ�[�g������Ό����ύX
		if( loadingFlag == true ){
			//���݈ʒu���Q�[�g�Ȃ琳��
			SetDirectPlayer( 2 );
			if( JumpPointSub( playerX, playerY, 2 ) == false ){
				JumpPointSub( (playerX -1), playerY, 6 );
				JumpPointSub( (playerX +1), playerY, 4 );
				JumpPointSub( playerX, (playerY -1), 2 );
				JumpPointSub( playerX, (playerY +1), 8 );
			}
		}
	}
}



//////////////////////////////////////////////////
//�L�����N�^�̈ړ�

public void moveCharacter( Graphics g )
{
	//�ϐ�
	int i;
	int x, y;
	char key;
	int iCount;

	//�v���[���[�^�[�����X�L�b�v
	if( g_iTurnSkip > 0 ){
		if( g_iTurnSkip %5 == 0 ) WonderingChara();
		--g_iTurnSkip;
		return;
	}

	if( (charaX %5 == 0) && (charaY %5 == 0) ){
		//���݈ʒu�̔w�i�`�F�b�N
		if( pointCheck(g) == true ) return;
		
		//�L�[���̓`�F�b�N
		if( (UpKey == true) && (currentKey == 8) ) key = 8;
		else if( (DownKey == true) && (currentKey == 2) ) key = 2;
		else if( (LeftKey == true) && (currentKey == 4) ) key = 4;
		else if( (RightKey == true) && (currentKey == 6) ) key = 6;
		else key = 0;
		
		//��莞�ԃL�[���͂��Ȃ��Ȃ珑��������
		if( (UpKey == false) && (DownKey == false) && (LeftKey == false) && (RightKey == false) ){
			if( countRepaint >= 10 ){
				if( g_bPaintMapMove == false ){
					x = mapX *10 +(countRepaint %11);
					for( i = 0 ; i < 11 ; ++i ){
						y = mapY *10 +i;
						if( objectAttribute[mapObject[y][x]][ATR_CROP2] == 0 ) displayCharacter( g, x, y );
					}
				}
				if( (countRepaint %11) == 0 ) displayConfigWindow( g, false, false, false, false );
			}
			++countRepaint;
		} else {
			countRepaint = 0;
		}
		//�J�n�܂��̓W�����v��Ɉ�莞�ԓ��͂��Ȃ���Α����݊J�n
		if( g_bRestPlayer == true ){
			if( TimerCount <= 10 ) iCount = countRepaint /2;
			else iCount = countRepaint;
			if( (iCount >= 64) && (iCount %8 == 0) && (countRepaint %4 == 0) ){
				if( iCount %16 == 0 ){
					if( moveDirect == 2 ) moveDirect = 4;
					else if( moveDirect == 4 ) moveDirect = 8;
					else if( moveDirect == 6 ) moveDirect = 2;
					else if( moveDirect == 8 ) moveDirect = 6;
					SetDirectPlayer( moveDirect );
				} else {
					SetDirectPlayer( moveDirect );
					++cropIDchara;
				}
			}
		}
		//��̃L�[�������ꂽ��
		if( (key == 8) || ((key == 0) && (UpKey == true)) ){
			moveDirect = 8;
			//��ʐ؂芷��
			if( (charaY == 0) && (mapY != 0) ){
				if( (map[playerY-1][playerX] != 0) || (g_bOldMap == 1) ){
					--mapY;
					mapFlagAll = true;
					mapFlagErase = true;
					g_bMapFlagMove = true;
					charaY = 5*10;
				}
			}
			else if( charaY != 0 ){
				//���̃`�F�b�N
				x = charaX /5 +mapX *10;
				y = charaY /5 +mapY *10 -1;
				if( directCheck(g,x,y) == true ){
					charaY = charaY - movePlus;
					playerY = y;
					WonderingChara();
				}
			}
		}
		//���̃L�[�������ꂽ��
		else if( (key == 2) || ((key == 0) && (DownKey == true)) ){
			moveDirect = 2;
			//��ʐ؂芷��
			if( (charaY == 5*10) && (mapY != g_iMapWidth/10-1) ){
				if( (map[playerY+1][playerX] != 0) || (g_bOldMap == 1) ){
					++mapY;
					mapFlagAll = true;
					mapFlagErase = true;
					g_bMapFlagMove = true;
					charaY = 0;
				}
			}
			else if( charaY != 5*10 ){
				//���̃`�F�b�N
				x = charaX /5 +mapX *10;
				y = charaY /5 +mapY *10 +1;
				if( directCheck(g,x,y) == true ){
					charaY = charaY + movePlus;
					playerY = y;
					WonderingChara();
				}
			}
		}
		//���̃L�[�������ꂽ��
		else if( (key == 4) || ((key == 0) && (LeftKey == true)) ){
			moveDirect = 4;
			//��ʐ؂芷��
			if( (charaX == 0) && (mapX != 0) ){
				if( (map[playerY][playerX-1] != 0) || (g_bOldMap == 1) ){
					--mapX;
					mapFlagAll = true;
					mapFlagErase = true;
					g_bMapFlagMove = true;
					charaX = 5*10;
				}
			}
			else if( charaX != 0 ){
				//���̃`�F�b�N
				x = charaX /5 +mapX *10 -1;
				y = charaY /5 +mapY *10;
				if( directCheck(g,x,y) == true ){
					charaX = charaX - movePlus;
					playerX = x;
					WonderingChara();
				}
			}
		}
		//�E�̃L�[�������ꂽ��
		else if( (key == 6) || ((key == 0) && (RightKey == true)) ){
			moveDirect = 6;
			//��ʐ؂芷��
			if( (charaX == 5*10) && (mapX != g_iMapWidth/10-1) ){
				if( (map[playerY][playerX+1] != 0) || (g_bOldMap == 1) ){
					++mapX;
					mapFlagAll = true;
					mapFlagErase = true;
					g_bMapFlagMove = true;
					charaX = 0;
				}
			}
			else if( charaX != 5*10 ){
				//���̃`�F�b�N
				x = charaX /5 +mapX *10 +1;
				y = charaY /5 +mapY *10;
				if( directCheck(g,x,y) == true ){
					charaX = charaX + movePlus;
					playerX = x;
					WonderingChara();
				}
			}
		}
	} else {
		if( moveDirect == 8 ) charaY = charaY - movePlus;
		else if( moveDirect == 2 ) charaY = charaY + movePlus;
		else if( moveDirect == 4 ) charaX = charaX - movePlus;
		else if( moveDirect == 6 ) charaX = charaX + movePlus;
		//�v���[���[�̌��݈ʒu�擾
		playerX = charaX /5 +mapX *10;
		playerY = charaY /5 +mapY *10;
	}
}



//////////////////////////////////////////////////
//���{��̕\��

public String getJapaneseToken( String str, int i )
{
	int j;
	String szToken;
	int iIndex;

	//�s��������擾
	g_iLast = i -1;
	szToken = tokenCutStirng( str, "\n" );
	//�������ɕϊ�
	szToken = szToken.toLowerCase();
	szToken = szToken.trim();
	szToken = szToken.replace( '.', ',' );
	//�󔒍폜
	for( j = 0 ; j < szToken.length() ; ++j ){
		iIndex = szToken.indexOf( " ", 0 );
		if( iIndex != -1 ) szToken = szToken.substring( 0, iIndex ) +szToken.substring( iIndex +1 );
		else break;
	}

	return szToken;
}


public String tokenCutStirng( String szToken, String szCut )
{
	int iIndex;
	String szWork = "";

	if( g_iLast == -2 ) return szWork;
	iIndex = g_iLast +1;
	g_iLast = szToken.indexOf( szCut, iIndex );
	if( g_iLast == -1 ){
		g_iLast = -2;
		szWork = szToken.substring( iIndex );
	} else {
		szWork = szToken.substring( iIndex, g_iLast );
	}

	return szWork;
}


public int indexOfSub( int iNumber, String szCompare, String szToken, int strWidth )
{
	int iIndex;

	iIndex = szToken.indexOf( szCompare );
	if( (iIndex != -1) && (iIndex < strWidth) ) return iNumber;
	return 0;
}


public int drawJapanese( Graphics g, String str, int xp, int yp, int strWidth, boolean bNoDraw, boolean bNoPage, boolean bMacro )
{
	//�ϐ���`
	int i, j;
	int x, y;
	int xp2, yp2;
	int iStrLine = 1;
	int iMode = 0;
	int iIndex;
	int strWidthCurrent;	//������̕�
	int iLast;
	int iLength;
	int iBox, iItem;
	int iParts;
	int iLimited;
	short iSourceParts;
	short iReplaceParts;
	char buff[] = new char[1];
	String szWork;
	String szWorkBuff;
	String szToken = new String("");
	int iData, iAtr;
	int iXpoint, iYpoint;
	String szStrX, szStrY;
	int iCrop, iXtop, iYtop, iXstart, iYstart, iXsize, iYsize;

	//�t�H���g�ݒ�
	if( bNoDraw == false ){
		Font fon = new Font( "Monospaced", Font.PLAIN, 18 );
		g.setFont(fon);
	}

	//�����̉��s�폜
	iIndex = str.indexOf( "\n", (str.length() -1) );
	if( iIndex == (str.length() -1) ){
		str = str.substring( 0, (str.length() -1) );
	}

	//������
	strWidthCurrent = 0;
	xp2 = xp;
	yp2 = yp;
	szWork = str;
	szWorkBuff = szWork.toLowerCase();
	//���y�[�W���o
	iIndex = szWorkBuff.indexOf( "<p>" );
	if( (iIndex != -1) && (g_iPageNumber >= 0) ){
		//������
		for( i = 0 ; i < g_iPageNumber ; ++i ){
			iIndex = szWorkBuff.indexOf( "<p>" );
			iIndex += 3;
			if( iIndex >= szWork.length() ) --iIndex;
			if( szWork.charAt(iIndex) == 10 ) ++iIndex;
			szWork = szWork.substring( iIndex, szWork.length() );
			szWorkBuff = szWork.toLowerCase();
		}
		if( bNoPage == false ) ++g_iPageNumber;
		
		//�\��������쐬
		iIndex = szWorkBuff.indexOf( "<p>" );
		if( iIndex != -1 ){
			str = szWork.substring( 0, iIndex );
			messageFlag = true;
		} else {
			str = szWork;
			if( bNoPage == false ) g_iPageNumber = -1;
		}
	}

	iLength = str.length();
	if( iLength == 0 ) return 0;
	for( i = 0 ; i < iLength ; ++i ){
		if( strWidthCurrent == 0 ){
			//�s��������擾
			szToken = getJapaneseToken( str, i );
			//�v���[���[�摜�̕ύX
			iMode = indexOfSub( 1, "$imgplayer=", szToken, strWidth );
			//YESNO�摜�ύX
			if( iMode == 0 ) iMode = indexOfSub( 2, "$imgyesno=", szToken, strWidth );
			//�����͍ő�l�ύX
			if( iMode == 0 ) iMode = indexOfSub( 3, "$hpmax=", szToken, strWidth );
			//�Z�[�u����
			if( iMode == 0 ) iMode = indexOfSub( 4, "$save=", szToken, strWidth );
			//�A�C�e��
			if( iMode == 0 ) iMode = indexOfSub( 5, "$item=", szToken, strWidth );
			//�f�t�H���g�ݒ�
			if( iMode == 0 ) iMode = indexOfSub( 6, "$default=", szToken, strWidth );
			//���}�b�v�݊�
			if( iMode == 0 ) iMode = indexOfSub( 7, "$oldmap=", szToken, strWidth );
			//�p�[�c���ꊷ��
			if( iMode == 0 ) iMode = indexOfSub( 8, "$parts=", szToken, strWidth );
			//�^�[���X�L�b�v
			if( iMode == 0 ) iMode = indexOfSub( 9, "$move=", szToken, strWidth );
			//�p�[�c���ꊷ��
			if( iMode == 0 ) iMode = indexOfSub( 10, "$map=", szToken, strWidth );
			//�i�s�����p�[�c���ꊷ��
			if( iMode == 0 ) iMode = indexOfSub( 11, "$dirmap=", szToken, strWidth );
			//�g�摜�ύX
			if( iMode == 0 ) iMode = indexOfSub( 12, "$imgframe=", szToken, strWidth );
			if( iMode == 0 ) iMode = indexOfSub( 13, "$imgbom=", szToken, strWidth );
			//�v���[���[�摜����
			if( iMode == 0 ) iMode = indexOfSub( 14, "$delplayer=", szToken, strWidth );
			//�t�F�C�X�\��
			if( iMode == 0 ) iMode = indexOfSub( 15, "$face=", szToken, strWidth );
			//�����ʌ���
			if( iMode == 0 ) iMode = indexOfSub( 16, "$effect=", szToken, strWidth );
			//�Q�[���I�[�o�[���W
			if( iMode == 0 ) iMode = indexOfSub( 17, "$gameover=", szToken, strWidth );
			//�g�p�^�A�C�e��
			if( iMode == 0 ) iMode = indexOfSub( 18, "$imgclick=", szToken, strWidth );
			//�X�e�[�^�X�ύX
			if( iMode == 0 ) iMode = indexOfSub( 19, "$status=", szToken, strWidth );
			//�X�e�[�^�X�ύX
			if( iMode == 0 ) iMode = indexOfSub( 20, "$effitem=", szToken, strWidth );
			//�s���Ɂ�����������΃X�L�b�v
			//if( (iMode == 0) && ((szToken.indexOf("$",0) == 0) || (szToken.indexOf("<",0) == 0)) ) iMode = 99;
			if( (iMode == 0) && (szToken.indexOf("$",0) == 0) ) iMode = 99;
			
			if( iMode != 0 ){
				g_iLast = -1;
				tokenCutStirng( szToken, "=" );
				//�}�N���͏d���������Ȃ��i�t�F�C�X�\���ȊO�j
				if( (bMacro == false) && (iMode != 15) ) iMode = 0;
				//�e��ύX
				if( (iMode == 1) || (iMode == 2) || (iMode == 13) || (iMode == 17) || (iMode == 18) ){
					//�摜�ʒu�ύX
					x = parse( tokenCutStirng( szToken, "," ) );
					y = parse( tokenCutStirng( szToken, "," ) );
					if( iMode == 1 ) g_iImgCharaCrop = x +y *10;
					else if( iMode == 2 ) SetYesNoCrop( x +y *10 );
					else if( iMode == 13 ) CROP_BOM = x +y *10;
					else if( iMode == 17 ){
						gameoverXp = x;
						gameoverYp = y;
					}
					else if( iMode == 18 ) g_iImgClickItem = x +y *10;
				}
				else if( iMode == 3 ) statusEnergyMax = parse( tokenCutStirng( szToken, "," ) );
				else if( iMode == 4 ) g_bSaveStop = parse( tokenCutStirng( szToken, "," ) );
				//�A�C�e��
				else if( iMode == 5 ){
					if( bNoDraw == true ){
						iBox = parse( tokenCutStirng( szToken, "," ) );
						iItem = parse( tokenCutStirng( szToken, "," ) );
						if( iBox == 0 ){
							arrangeItem( iItem );
						} else {
							itemBox[iBox-1] = iItem;
							arrangeItem( 0 );
						}
					}
					//�A�C�e���{�b�N�X�ĕ`��
					configFlag = true;
				}
				else if( iMode == 6 ) g_bDefault = parse( tokenCutStirng( szToken, "," ) );
				else if( iMode == 7 ) g_bOldMap = parse( tokenCutStirng( szToken, "," ) );
				//�p�[�c���ꊷ��
				else if( iMode == 8 ){
					iSourceParts = (short)parse( tokenCutStirng( szToken, "," ) );
					iReplaceParts = (short)parse( tokenCutStirng( szToken, "," ) );
					iParts = parse( tokenCutStirng( szToken, "," ) );
					iLimited = parse( tokenCutStirng( szToken, "," ) );
					if( iLimited == 0 ){
						for( x = 0 ; x < g_iMapWidth ; ++x ){
							for( y = 0 ; y < g_iMapWidth ; ++y ){
								if( (iParts == 1) && (iSourceParts == map[y][x]) ) map[y][x] = iReplaceParts;
								else if( (iParts == 0) && (iSourceParts == mapObject[y][x]) ) mapObject[y][x] = iReplaceParts;
							}
						}
					} else {
						for( x = 0 ; x < 11 ; ++x ){
							for( y = 0 ; y < 11 ; ++y ){
								if( (iParts == 1) && (iSourceParts == map[y +mapY *10][x +mapX *10]) ) map[y +mapY *10][x +mapX *10] = iReplaceParts;
								else if( (iParts == 0) && (iSourceParts == mapObject[y +mapY *10][x +mapX *10]) ) mapObject[y +mapY *10][x +mapX *10] = iReplaceParts;
							}
						}
					}
					if( g_bPaintMapMove == false ) mapFlagAll = true;
				}
				//�^�[���X�L�b�v
				else if( iMode == 9 ){
					g_iTurnSkip = parse( tokenCutStirng( szToken, "," ) ) *5;
				}
				//�p�[�c�o��
				else if( iMode == 10 ){
					//System.out.println(szToken);
					iData = parse( tokenCutStirng( szToken, "," ) );
					szStrX = tokenCutStirng( szToken, "," );
					x = parse( szStrX );
					szStrY = tokenCutStirng( szToken, "," );
					y = parse( szStrY );
					iAtr = parse( tokenCutStirng( szToken, "," ) );
					iXpoint = g_iXpoint;
					iYpoint = g_iYpoint;
					//���΍��W������
					if( szStrX.indexOf("+",0) == 0 ){
						szStrX = szStrX.replace( '+' ,'0' );
						x = parse( szStrX );
						iXpoint += x;
					} else if( szStrX.indexOf("-",0) == 0 ) iXpoint += x;
					else iXpoint = x;
					
					if( szStrY.indexOf("+",0) == 0 ){
						szStrY = szStrY.replace( '+' ,'0' );
						y = parse( szStrY );
						iYpoint += y;
					} else if( szStrY.indexOf("-",0) == 0 ) iYpoint += y;
					else iYpoint = y;
					//�v���[���[�ʒu
					if( szStrX.indexOf("p",0) == 0 ) iXpoint = playerX;
					if( szStrY.indexOf("p",0) == 0 ) iYpoint = playerY;
					//�p�[�c�o��
					if( (iXpoint >= 0) && (iXpoint <= g_iMapWidth-1) && (iYpoint >= 0) && (iYpoint <= g_iMapWidth-1) ){
						if( iAtr == 0 ) mapObject[iYpoint][iXpoint] = (short)iData;
						else map[iYpoint][iXpoint] = (short)iData;
					}
					if( g_bPaintMapMove == false ) mapFlagAll = true;
				}
				//�p�[�c�o��
				else if( iMode == 11 ){
					iData = parse( tokenCutStirng( szToken, "," ) );
					x = parse( tokenCutStirng( szToken, "," ) );
					iAtr = parse( tokenCutStirng( szToken, "," ) );
					iXpoint = playerX;
					iYpoint = playerY;
					if( moveDirect == 6 ) iXpoint += x;
					else if( moveDirect == 4 ) iXpoint -= x;
					else if( moveDirect == 2 ) iYpoint += x;
					else if( moveDirect == 8 ) iYpoint -= x;
					//�p�[�c�o��
					if( (iXpoint >= 0) && (iXpoint <= g_iMapWidth-1) && (iYpoint >= 0) && (iYpoint <= g_iMapWidth-1) ){
						if( iAtr == 0 ) mapObject[iYpoint][iXpoint] = (short)iData;
						else map[iYpoint][iXpoint] = (short)iData;
					}
					if( g_bPaintMapMove == false ) mapFlagAll = true;
				}
				//�g�摜�ύX
				else if( iMode == 12 ){
					iAtr = parse( tokenCutStirng( szToken, "," ) );
					x = parse( tokenCutStirng( szToken, "," ) );
					y = parse( tokenCutStirng( szToken, "," ) );
					if( iAtr == 0 ) CROP_ENERGY = x +y *10;
					else if( iAtr == 1 ) CROP_STRENGTH = x +y *10;
					else if( iAtr == 2 ) CROP_DEFENCE = x +y *10;
					else if( iAtr == 3 ) CROP_GOLD = x +y *10;
					else if( iAtr == 4 ) CROP_STFRAME = x +y *10;
					else if( iAtr == 5 ) CROP_ITEMFRAME = x +y *10;
					else if( iAtr == 6 ) CROP_MAINFRAME = x +y *10;
				}
				//�v���[���[�摜����
				else if( iMode == 14 ){
					g_bDelPlayer = parse( tokenCutStirng( szToken, "," ) );
				}
				//�t�F�C�X�\��
				else if( iMode == 15 ){
					iXtop = parse( tokenCutStirng( szToken, "," ) );
					iYtop = parse( tokenCutStirng( szToken, "," ) );
					iXstart = parse( tokenCutStirng( szToken, "," ) );
					iYstart = parse( tokenCutStirng( szToken, "," ) );
					iXsize = parse( tokenCutStirng( szToken, "," ) );
					iYsize = parse( tokenCutStirng( szToken, "," ) );
					for( x = 0 ; x < iXsize ; ++x ){
						for( y = 0 ; y < iYsize ; ++y ){
							iCrop = (x +iXstart) +(y +iYstart) *10;
							g.drawImage( imgCrop[iCrop], iXtop +x *40, iYtop +y *40, this );
						}
					}
				}
				//�����ʌ���
				else if( iMode == 16 ){
					g_iEffWait = parse( tokenCutStirng( szToken, "," ) );
					for( j = 0 ; j < 4 ; ++j ){
						iXtop = parse( tokenCutStirng( szToken,"," ) );
						iYtop = parse( tokenCutStirng( szToken,"," ) );
						g_iEffCrop[j] = iXtop +iYtop *10;
					}
				}
				//�X�e�[�^�X�ύX
				else if( iMode == 19 ){
					iAtr = parse( tokenCutStirng( szToken, "," ) );
					if( iAtr == 0 ) statusEnergy = parse( tokenCutStirng( szToken, "," ) );
					else if( iAtr == 1 ) statusStrength = parse( tokenCutStirng( szToken, "," ) );
					else if( iAtr == 2 ) statusDefence = parse( tokenCutStirng( szToken, "," ) );
					else if( iAtr == 3 ) statusGold = parse( tokenCutStirng( szToken, "," ) );
					else if( iAtr == 4 ) g_iStep = parse( tokenCutStirng( szToken, "," ) );
				}
				else if( iMode == 20 ) g_bAnmItem = parse( tokenCutStirng( szToken, "," ) );
				//�����񐮗�
				iIndex = str.indexOf( "\n", i );
				if( (i == 0) && (iIndex == -1) ){
					return 0;
				} else {
					i = iIndex;
					if( i == -1 ) return (iStrLine -1);
					continue;
				}
			}
			//�R�����g�ȍ~���폜
			iIndex = szToken.indexOf( "<c>" );
			if( (iIndex != -1) && (iIndex < strWidth) ){
				//�����񐮗�
				if( i == 0 ){
					return 0;
				} else {
					return (iStrLine -1);
				}
			}
		}
		//���s�̏ꍇ
		if( str.charAt(i) == 10 ){
			xp2 = xp;
			yp2 += 22;
			strWidthCurrent = 0;
			//�I�s�̉��s�͊܂߂Ȃ�
			if( i < (iLength -1) ) ++iStrLine;
			continue;
		}
		buff[0] = str.charAt(i);
		
		if( ((buff[0] & 0xff00) == 0) && (buff[0] != '�~') && (buff[0] != '��') ){
			if( bNoDraw == false ){
				g.setColor( Color.gray );
				g.drawString( new String(buff), xp2 +1 +1, yp2 +17 );
				g.setColor( Color.black );
				g.drawString( new String(buff), xp2 +1, yp2 +17 );
			}
			//���p����
			xp2 += 10;
			++strWidthCurrent;
		} else {
			if( bNoDraw == false ){
				g.setColor( Color.gray );
				g.drawString( new String(buff), xp2 +2 +1, yp2 +16 );
				g.setColor( Color.black );
				g.drawString( new String(buff), xp2 +2, yp2 +16 );
			}
			//�S�p����
			xp2 += 20;
			strWidthCurrent += 2;
		}
		if( strWidthCurrent >= strWidth *2 ){
			if( i < (iLength -1) ){
				//�s�������_�A���s�̏ꍇ�̓X�L�b�v
				if( (str.charAt(i+1) == '�B') || (str.charAt(i+1) == '�A') || (str.charAt(i+1) == 10) ) continue;
			}
			xp2 = xp;
			yp2 += 22;
			strWidthCurrent = 0;
			//�I�s�̉��s�͊܂߂Ȃ�
			if( i < (iLength -1) ) ++iStrLine;
		}
	}
	return iStrLine;
}



//////////////////////////////////////////////////
//���o�����{��\��

public void drawJapaneseFrameSub( Graphics g, String str, int xp, int yp, int xsize, int ysize, boolean bNoPage )
{
	g.setColor( new Color(96,96,96) );
	g.fillRoundRect( xp-2, yp-2, xsize+4, ysize+4, 30, 30 );
	g.setColor( Color.white );
	g.fillRoundRect( xp, yp, xsize, ysize, 30, 30 );
	drawJapanese( g, str, xp+7, yp+9, 16, false, bNoPage, false );
	if( yesnoFlag == true ){
		g.drawImage( imgCrop[CROP_YES], yesnoX+2, g_iYesnoY+2, this );
		g.drawImage( imgCrop[CROP_NO], yesnoX+2 +40, g_iYesnoY+2, this );
	}
}


public boolean drawJapaneseFrame( Graphics g, Graphics gGlobal, String str, int xp, int yp )
{
	int xsize, ysize;
	xsize = 20*16+20;
	ysize = 22*7+20;
	int iStrLine;

	//������̍s���ݒ�$$
	iStrLine = drawJapanese( g, str, 0, 0, 16, true, true, true );
	if( iStrLine == 0 ) return false;
	ysize = 22*iStrLine +20;
	if( yesnoFlag == true ) ysize += 40;

	//�������o��
	if( yp == -3 ){
		yp = 440 /2 -(ysize /2);
	}
	//��t�����o��
	if( yp == -2 ){
		yp = 440 /4 -(ysize /2) +10;
		if( yp < 20 ) yp = 20;
	}
	//���t�����o��
	if( yp == -1 ){
		yp = 440 /4 *3 -(ysize /2) -10;
		if( (yp +ysize > 420) ) yp = 420 -ysize;
	}
	//YesNo�{�^���ʒu�ݒ�
	g_iYesnoY = yp +22*iStrLine +8;

	if( gGlobal != null ){
		//�ᑬ�}�V���p�̐�s�`��
		drawJapaneseFrameSub( gGlobal, str, xp, yp, xsize, ysize, true );
		//����`�������p�̖ʂɕ`��
		paintMapAll( gGlobal, false );
		drawJapaneseFrameSub( g, str, xp, yp, xsize, ysize, false );
	} else {
		//�w��ʂɕ`��
		drawJapaneseFrameSub( g, str, xp, yp, xsize, ysize, false );
	}

	return true;
}



//////////////////////////////////////////////////
//��莞�Ԃ̃E�F�C�g

public void twait( int countWait )
{
	//�^�C�}�ݒ�
	try {
		Thread.sleep( countWait );
	} catch( InterruptedException e ){
		System.err.println("110 Thread Error!");
	}
}

public void twait( int countWait, Graphics g )
{
	paintMapMove( g, true, 0, 0 );
	twait( countWait );
}



//////////////////////////////////////////////////
//�o�C�i���E�}�b�v�f�[�^�̓ǂݍ���

public void loadMapDataSub( String filename )
{
	int i, j;
	int fileSize;
	InputStream fp;		//�t�@�C�����o��
	byte cByteBuffSotck[];	//�f�[�^�X�g�b�N�p

	try{
		fp = new URL( getDocumentBase(), filename ).openStream();
		j = 0;
		while( (fileSize = fp.read( byteBuffPress, j ,FREAD_BLOCK )) != -1 ){
			j += fileSize;
			//�K��ő�e�ʂ𒴂���Η̈�g�����čĎ��s$$
			if( (j +FREAD_BLOCK) >= (g_iBlockByteBuffPress *MEM_BLOCK) ){
				//�X�g�b�N�Ƀf�[�^�ޔ�
				cByteBuffSotck = new byte[j];
				for( i = 0 ; i < j ; ++i ) cByteBuffSotck[i] = byteBuffPress[i];
				//�̈�g��
				++g_iBlockByteBuffPress;
				byteBuffPress = new byte[g_iBlockByteBuffPress *MEM_BLOCK];
				//�X�g�b�N����f�[�^���A
				for( i = 0 ; i < j ; ++i ) byteBuffPress[i] = cByteBuffSotck[i];
			}
		}
		g_iFileSize = j;
		fp.close();
	} catch( MalformedURLException e ){
		g_bIOError = true;
		System.err.println( "120 URL Error!" );
	} catch( FileNotFoundException e ){
		gFileNotFound = true;
		System.err.println( "121 File not Found!" );
	} catch( IOException e ){
		g_bIOError = true;
		System.err.println( "122 MapRead Error!" );
	} catch( SecurityException e ){
		g_bIOError = true;
		System.err.println( "123 MapRead Error!" );
	} catch( Exception e ){
		g_bIOError = true;
		System.err.println( "124 MapRead Error!" );
	}
}


public void loadMapData( Graphics g, String filename, boolean bLoadFlag )
{
	//�ϐ���`
	int i, j, k;
	int x, y;
	int data;
	int PassNumber;
	//�}�b�v����l
	int mapAtrMax; // = MAP_ATR_MAX;
	int objAtrMax; // = OBJECT_ATR_MAX;
	int point2 = 0;
	int iDataCharaX, iDataCharaY;
	int iVersion;
	int idNumber;

	//�t�@�C���̓ǂݍ���
	if( bLoadFlag == true ){
		loadMapDataSub( filename );
		if( (gFileNotFound == true) || (g_bIOError == true) ) return;
		//�̈�m��
		g_iBlockByteBuff = g_iBlockByteBuffPress;
		byteBuff = new byte[g_iBlockByteBuff *MEM_BLOCK];
	}
	//�f�[�^��
	extractMapData();
	point2 = g_iPointerExtract;

	//�o�[�V�����擾
	iVersion = unsignedByte( byteBuff[DATA_MAP_VERSION] );

	if( iVersion <= 29 ){
		DATA_MAP_COUNT = 3;
		DATA_OBJECT_COUNT = 4;
		DATA_CHARA_X = 5;
		DATA_CHARA_Y = 6;
		DATA_OVER_X = 18;
		DATA_OVER_Y = 19;
		DATA_ITEM = 20;
		gameoverXp = unsignedByte( byteBuff[DATA_OVER_X] );
		gameoverYp = unsignedByte( byteBuff[DATA_OVER_Y] );
		iDataCharaX = unsignedByte( byteBuff[DATA_CHARA_X] );
		iDataCharaY = unsignedByte( byteBuff[DATA_CHARA_Y] );
		g_iMapPartsMax = unsignedByte( byteBuff[DATA_MAP_COUNT] );
		g_iObjectPartsMax = unsignedByte( byteBuff[DATA_OBJECT_COUNT] );
		g_bOldMap = 1;
 	} else {
		DATA_MAP_COUNT = 34;
		DATA_OBJECT_COUNT = 36;
		DATA_CHARA_X = 38;
		DATA_CHARA_Y = 40;
		DATA_OVER_X = 42;
		DATA_OVER_Y = 44;
		DATA_ITEM = 60;
		gameoverXp = unsignedByte( byteBuff[DATA_OVER_X] ) + unsignedByte( byteBuff[DATA_OVER_X+1] ) * 0x100;
		gameoverYp = unsignedByte( byteBuff[DATA_OVER_Y] ) + unsignedByte( byteBuff[DATA_OVER_Y+1] ) * 0x100;
		iDataCharaX = unsignedByte( byteBuff[DATA_CHARA_X] ) + unsignedByte( byteBuff[DATA_CHARA_X+1] ) * 0x100;
		iDataCharaY = unsignedByte( byteBuff[DATA_CHARA_Y] ) + unsignedByte( byteBuff[DATA_CHARA_Y+1] ) * 0x100;
		g_iMapPartsMax = unsignedByte( byteBuff[DATA_MAP_COUNT] ) + unsignedByte( byteBuff[DATA_MAP_COUNT+1] ) * 0x100;
		g_iObjectPartsMax = unsignedByte( byteBuff[DATA_OBJECT_COUNT] ) + unsignedByte( byteBuff[DATA_OBJECT_COUNT+1] ) * 0x100;
	}

	//�f�[�^�ݒ�
	statusEnergyMax = unsignedByte( byteBuff[DATA_STATUS_ENERGYMAX] ) + unsignedByte( byteBuff[DATA_STATUS_ENERGYMAX+1] ) * 0x100;
	statusEnergy = unsignedByte( byteBuff[DATA_STATUS_ENERGY] ) + unsignedByte( byteBuff[DATA_STATUS_ENERGY+1] ) * 0x100;
	statusStrength = unsignedByte( byteBuff[DATA_STATUS_STRENGTH] ) + unsignedByte( byteBuff[DATA_STATUS_STRENGTH+1] ) * 0x100;
	statusDefence = unsignedByte( byteBuff[DATA_STATUS_DEFENCE] ) + unsignedByte( byteBuff[DATA_STATUS_DEFENCE+1] ) * 0x100;
	statusGold = unsignedByte( byteBuff[DATA_STATUS_GOLD] ) + unsignedByte( byteBuff[DATA_STATUS_GOLD+1] ) * 0x100;
	for( i = 0 ; i < 12 ; ++i ) itemBox[i] = unsignedByte( byteBuff[DATA_ITEM +i] );
	g_iMapWidth = unsignedByte( byteBuff[DATA_MAP_SIZE] ) + unsignedByte( byteBuff[DATA_MAP_SIZE+1] ) * 0x100;
	g_iMesNumberMax = unsignedByte( byteBuff[DATA_MES_NUMBER] ) + unsignedByte( byteBuff[DATA_MES_NUMBER+1] ) * 0x100;
	//	System.out.println( "jump = " +charaX +" " +charaY );

	//���ʃo�[�W�����݊�
	if( iVersion < 28 ) g_iMapWidth = 71;
	else if( iVersion <= 29 ) g_iMapWidth = 101;

	//�v���[���[�����ʒu
	JumpPoint( iDataCharaX, iDataCharaY );

	//�|�C���^�����l
	g_iPointer = 100;
	//���ʃo�[�W��������̓ǂݍ��݃v���e�N�g
	if( iVersion >= 29 ) g_iPointer = 90;

	//�}�b�v�f�[�^�̈�擾$$
	if( bLoadFlag == true ){
		map = new short[g_iMapWidth][g_iMapWidth];
		mapObject = new short[g_iMapWidth +1][g_iMapWidth +1];
		QSaveMap = new short[g_iMapWidth][g_iMapWidth];
		QSaveMapObject = new short[g_iMapWidth +1][g_iMapWidth +1];
	}

	//�}�b�v�f�[�^
	for( x = 0 ; x < g_iMapWidth ; ++x ){
		for( y = 0 ; y < g_iMapWidth ; ++y ){
			if( iVersion <= 29 ){
				map[x][y] = (short)unsignedByte(byteBuff[g_iPointer]);
				++g_iPointer;
			} else {
				map[x][y] = (short)(unsignedByte( byteBuff[g_iPointer] ) + unsignedByte( byteBuff[g_iPointer+1] ) * 0x100);
				g_iPointer += 2;
			}
			//�͈͊O�̃p�[�c�폜
			if( map[x][y] >= g_iMapPartsMax ) map[x][y] = 0;
		}
	}
	for( x = 0 ; x < g_iMapWidth ; ++x ){
		for( y = 0 ; y < g_iMapWidth ; ++y ){
			if( iVersion <= 29 ){
				mapObject[x][y] = (short)unsignedByte(byteBuff[g_iPointer]);
				++g_iPointer;
			} else {
				mapObject[x][y] = (short)(unsignedByte( byteBuff[g_iPointer] ) + unsignedByte( byteBuff[g_iPointer+1] ) * 0x100);
				g_iPointer += 2;
			}
			//�͈͊O�̃p�[�c�폜
			if( mapObject[x][y] >= g_iObjectPartsMax ) mapObject[x][y] = 0;
		}
	}
	if( iVersion <= 29 ){
		mapAtrMax = 40;
		objAtrMax = 40;
	} else {
		mapAtrMax = 60;
		objAtrMax = 60;
	}
	MAP_ATR_MAX = mapAtrMax;
	OBJECT_ATR_MAX = objAtrMax;

	//�}�b�v�L�����N�^�̈�擾
	if( bLoadFlag == true ){
		mapAttribute = new int[g_iMapPartsMax][60];
	}
	for( i = 0 ; i < g_iMapPartsMax ; ++i ){
		for( j = 0 ; j < mapAtrMax ; ++j ){
			if( (j == ATR_CROP1) || (j == ATR_CROP2) ){
				if( bLoadFlag == true ) mapAttribute[i][j] = 0;
				g_iPointer += 2;
				continue;
			}
			mapAttribute[i][j] = unsignedByte(byteBuff[g_iPointer]) + unsignedByte(byteBuff[g_iPointer+1]) * 0x100;
			g_iPointer += 2;
		}
	}

	//�I�u�W�F�N�g�L�����N�^�̈�擾
	if( bLoadFlag == true ){
		objectAttribute = new int[g_iObjectPartsMax][60];
	}
	for( i = 0 ; i < g_iObjectPartsMax ; ++i ){
		for( j = 0 ; j < objAtrMax ; ++j ){
			if( (j == ATR_CROP1) || (j == ATR_CROP2) ){
				if( bLoadFlag == true ) objectAttribute[i][j] = 0;
				g_iPointer += 2;
				continue;
			}
			objectAttribute[i][j] = unsignedByte(byteBuff[g_iPointer]) + unsignedByte(byteBuff[g_iPointer+1]) * 0x100;
			g_iPointer += 2;
		}
	}
	//System.out.println( "Test = " +g_iObjectPartsMax );

	//���ʌ݊��g���L�����N�^�ϊ�$$$$
	int dataChara, dataMode;
	if( iVersion <= 29 ){
		for( j = 0 ; j < g_iMapPartsMax ; ++j ){
			for( i = 9 ; i >= 0 ; --i ){
				dataChara = mapAttribute[j][20+i*2];
				dataChara = dataChara & 0xff;
				dataMode = mapAttribute[j][20+i*2];
				dataMode = dataMode >> 8;
				x = mapAttribute[j][20+i*2+1];
				x = x & 0xff;
				y = mapAttribute[j][20+i*2+1];
				y = y >> 8;
				if( x == 250 ) x = 9000;
				else if( x > 100 ) x += (10000 -160);
				if( y == 250 ) y = 9000;
				else if( y > 100 ) y += (10000 -160);
				
				mapAttribute[j][20+i*4] = dataChara;
				mapAttribute[j][20+i*4+3] = dataMode;
				mapAttribute[j][20+i*4+1] = x;
				mapAttribute[j][20+i*4+2] = y;
			}
			if( mapAttribute[j][ATR_TYPE] == MAP_LOCALGATE ){
				if( mapAttribute[j][ATR_JUMP_X] > 100 ) mapAttribute[j][ATR_JUMP_X] += (10000 -160);
				if( mapAttribute[j][ATR_JUMP_Y] > 100 ) mapAttribute[j][ATR_JUMP_Y] += (10000 -160);
			}
		}
		for( j = 0 ; j < g_iObjectPartsMax ; ++j ){
			for( i = 9 ; i >= 0 ; --i ){
				dataChara = objectAttribute[j][20+i*2];
				dataChara = dataChara & 0xff;
				dataMode = objectAttribute[j][20+i*2];
				dataMode = dataMode >> 8;
				x = objectAttribute[j][20+i*2+1];
				x = x & 0xff;
				y = objectAttribute[j][20+i*2+1];
				y = y >> 8;
				if( x == 250 ) x = 9000;
				else if( x > 100 ) x += (10000 -160);
				if( y == 250 ) y = 9000;
				else if( y > 100 ) y += (10000 -160);
				
				objectAttribute[j][20+i*4] = dataChara;
				objectAttribute[j][20+i*4+3] = dataMode;
				objectAttribute[j][20+i*4+1] = x;
				objectAttribute[j][20+i*4+2] = y;
			}
			if( objectAttribute[j][ATR_TYPE] == OBJECT_LOCALGATE ){
				if( objectAttribute[j][ATR_JUMP_X] > 100 ) objectAttribute[j][ATR_JUMP_X] += (10000 -160);
				if( objectAttribute[j][ATR_JUMP_Y] > 100 ) objectAttribute[j][ATR_JUMP_Y] += (10000 -160);
			}
		}
	}
	//�����_���L�����N�^
	for( x = 0 ; x < g_iMapWidth ; ++x ){
		for( y = 0 ; y < g_iMapWidth ; ++y ){
			RandomCharacter( x, y );
		}
	}
	//���b�Z�[�W�f�[�^�̓ǂ݂���
	g_iPointer = point2;
	for( i = 0 ; i < g_iFileSize ; ++i ) byteBuff[i] = byteBuffPress[i];

	//�V�Ï��ԍ�
	if( iVersion >= 30 ) worldPassword = loadMapString();
	//�p�[�c�p���b�Z�[�W
	if( iVersion <= 29 ) g_iMesNumberMax = 400;
	strMessage = new String[g_iMesNumberMax];
	for( i = 0 ; i < g_iMesNumberMax ; ++i ){
		strMessage[i] = loadMapString();
	}
	//���̑��f�[�^
	worldName = loadMapString();
	if( iVersion <= 29 ) worldPassword = loadMapString();
	else loadMapString();
	if( iVersion >= 29 ) worldPassNumber = (((parse(worldPassword) /10) -1197) /17) -2357;
	else worldPassNumber = parse( worldPassword );
	mojicgName = loadMapString();
	mapcgName = loadMapString();
	//�V�X�e�����b�Z�[�W
	if( iVersion >= 30 ){
		for( i = 0 ; i < 20 ; ++i ) g_szMessageSystem[i] = loadMapString();
	} else {
		for( i = 0 ; i < 20 ; ++i ) g_szMessageSystem[i] = "";
	}
	//�A�C�e���X�e�[�^�X
	arrangeItem( 0 );

	//HTML�ɂ�鏉���p�����[�^�̐ݒ�
	if( getParameter("Id") != null ){
		//�Ïؔԍ��v�Z
		idNumber = parse( getParameter("Energy") ) *7;
		idNumber += parse( getParameter("Strength") ) *11;
		idNumber += parse( getParameter("Defence") ) *19;
		idNumber += parse( getParameter("Gold") ) *5;
		idNumber += parse( getParameter("PlayerX") ) *17;
		idNumber += parse( getParameter("PlayerY") ) *21;
		for( i = 0 ; i < 12 ; ++i ){
			if( getParameter("item"+(i+1)) != null ) idNumber += parse( getParameter("item"+(i+1)) ) *17;
		}
		idNumber = idNumber %9999;
		if( parse(worldPassword) != 0 ) idNumber *= worldPassNumber;
		idNumber = idNumber %9999;
		
		if( idNumber == parse(getParameter("Id")) ){
			statusEnergy = parse( getParameter("Energy") );
			statusStrength = parse( getParameter("Strength") );
			statusDefence = parse( getParameter("Defence") );
			statusGold = parse( getParameter("Gold") );
			//�A�C�e������
			for( i = 0 ; i < 12 ; ++i ){
				if( getParameter("item"+(i+1)) != null ) itemBox[i] = parse( getParameter("item"+(i+1)) );
			}
			//�A�C�e���X�e�[�^�X
			arrangeItem( 0 );
			statusStrength -= itemStrength;
			statusDefence -= itemDefence;
			
			if( getParameter("Flag") != null ){
				if( getParameter("Flag").equals("ON") || getParameter("Flag").equals("on") ){
					//���݈ʒu�ݒ�
					charaX = parse( getParameter("PlayerX") );
					mapX = charaX /10;
					charaX = (charaX %10) *5;
					charaY = parse( getParameter("PlayerY") );
					mapY = charaY /10;
					charaY = (charaY %10) *5;
				}
			}
		}
	}
}


//�o�C�g���C���e�W���[�ɕϊ�
public int unsignedByte( byte numberByte )
{
	int number;

	number = (int)numberByte;
	if( number < 0 ) number += 0x100;
	return number;
}


//������̓ǂݍ���
public String loadMapString()
{
	int length;
	String str = new String();
	byte data1, data2;
	byte dataByte[] = new byte[1];

	for( length = 0 ; length < 1500 ; ++length ){
		if( (byteBuff[g_iPointer +length *2] == 0) && (byteBuff[g_iPointer +length *2 +1] == 0) ) break;
		dataByte[0] = byteBuff[g_iPointer +length *2];
		data2 = byteBuff[g_iPointer +length *2 +1];
		//������̎��o��
		str = (str + (new String(dataByte,data2)) );
	}
	g_iPointer += length *2 +2;

	return str;
}


//�}�b�v�f�[�^�W�J
public void extractMapData()
{
	//�ϐ���`
	int i, j, k;
	int counter, maxim;
	int checkData = 0;
	int checkDataBuff;

	g_bCompleteExtract = false;

	for( i = 0, j = 0, counter = 0 ; ; ++i ){
		if( (byteBuffPress[i] == 0) && (byteBuffPress[i+1] == 0) && (byteBuffPress[i+2] == 0) ) break;
		//�������A�����Ă���Ή𓀏���
		byteBuff[j] = byteBuffPress[i];
		++j;
		if( byteBuffPress[i] == byteBuffPress[i+1] ){
			maxim = unsignedByte( byteBuffPress[i+2] );
			for( counter = 0 ; counter < maxim ; ++counter ){
				byteBuff[j] = byteBuffPress[i];
				++j;
			}
			i += 2;
		}
		//�}�b�v�T�C�Y�ƃp�[�c���̃f�[�^����K�v�̈�擾$$
		//�ő�T�C�Y�𒴂������ȂƂ��͗̈�g�����čĎ��s
		if( (j +255) >= (g_iBlockByteBuff *MEM_BLOCK) ){
			++g_iBlockByteBuff;
			byteBuff = new byte[g_iBlockByteBuff *MEM_BLOCK];
			extractMapData();
			if( g_bCompleteExtract == true ) return;
		}
	}
	System.out.println( "ExtractData = " +j +" " +i );

	//����ԍ��`�F�b�N
	if( unsignedByte(byteBuff[DATA_MAP_VERSION]) >= 29 ){
		for( k = 2 ; k < j ; ++k ) checkData += (byteBuff[k] *(k %8 +1));
		//�}�C�i�X���u
		checkData = (checkData % 0x10000) & 0xffff;
		//����
		checkDataBuff = unsignedByte(byteBuff[DATA_CHECK]) +unsignedByte(byteBuff[DATA_CHECK+1]) * 0x100;
		if( checkDataBuff != checkData ) gDataBroken = true;
		System.out.println( "buff = " +checkData +" " +checkDataBuff );
	}
	g_bCompleteExtract = true;
	g_iPointerExtract = i +3;
}


//�����_���L�����N�^
public void RandomCharacter( int x, int y )
{
	if( objectAttribute[mapObject[x][y]][ATR_TYPE] == OBJECT_RANDOM ){
		int number = random.nextInt() %10;
		if( number < 0 ) number = -number;
		mapObject[x][y] = (short)objectAttribute[mapObject[x][y]][10+number];
		if( mapObject[x][y] >= g_iObjectPartsMax ) mapObject[x][y] = 0;
	}
}



//////////////////////////////////////////////////
//�O���t�B�b�N�̓ǂݍ���

public void graphicLoading( Graphics g )
{
	//�ϐ���`
	int i, j, k;
	int x, y;
	int maxY = 4;
	int height;

	//�}�b�v�ǂݍ��݁A���f�B�A�g���b�J�[�ɓo�^
	imgMap = getImage( getDocumentBase(), mapcgName );
	tracker.addImage( imgMap, 0 );

	//�C���[�W���쐬�����܂ő҂�
	try {
		tracker.waitForID(0);
	} catch( InterruptedException e ){
		gImageNotFound = true;
		System.err.println("100 Tracker Error!");
		return;
	} catch( Exception e ){
		gImageNotFound = true;
		System.err.println("101 waitForID Error!");
		return;
	}
	LoadingMessage( g, 3 );

	//�摜�̓ǂݍ��݃`�F�b�N
	if( (tracker.statusAll(false) & MediaTracker.ERRORED) != 0 ){
		gImageNotFound = true;
		return;
	}

	//�摜�����擾
	height = imgMap.getHeight( this );

	//�N���b�v�ԍ��v�Z
	for( i = 0 ; i < g_iMapPartsMax ; ++i ){
		x = mapAttribute[i][ATR_X] /40;
		y = mapAttribute[i][ATR_Y] /40;
		//�͈͊O�̉摜�͏���
		if( y >= ((height +39) /40) ){
			mapAttribute[i][ATR_CROP1] = 0;
		} else {
			if( y > maxY ) maxY = y;
			mapAttribute[i][ATR_CROP1] = x +y *10;
		}
	}
	for( i = 0 ; i < g_iObjectPartsMax ; ++i ){
		x = objectAttribute[i][ATR_X] /40;
		y = objectAttribute[i][ATR_Y] /40;
		if( y >= ((height +39) /40) ){
			objectAttribute[i][ATR_CROP1] = 0;
		} else {
			if( y > maxY ) maxY = y;
			objectAttribute[i][ATR_CROP1] = x +y *10;
		}
		x = objectAttribute[i][ATR_X2] /40;
		y = objectAttribute[i][ATR_Y2] /40;
		if( y >= ((height +39) /40) ){
			objectAttribute[i][ATR_CROP2] = 0;
		} else {
			if( y > maxY ) maxY = y;
			objectAttribute[i][ATR_CROP2] = x +y *10;
		}
	}

	//�̈�擾
	if( height != -1 ) g_iCropMax = ((height -1) /40 +1) *10;
	else g_iCropMax = (maxY +1) *10;
	imgCrop = new Image[g_iCropMax];

	//�摜�؂蕪��
	for( cropID = 0 ; cropID < g_iCropMax ; ++cropID ){
		imgCrop[cropID] = createImage( new FilteredImageSource( imgMap.getSource(), new CropImageFilter((cropID %10)*40, (cropID /10) *40, 40, 40) ));
	}
	//���f�B�A�g���b�J�[�ɓo�^
	for( k = 0 ; k < cropID ; ++k ){
		tracker.addImage( imgCrop[k], 0 );
	}
	//�C���[�W���쐬�����܂ő҂�
	try {
		tracker.waitForID(0);
	} catch( InterruptedException e ){
		System.err.println("102 Tracker Error!");
	}
	LoadingMessage( g, 4 );

	//���摜���
	imgMap.flush();

	System.out.println( "Crop ID = " +cropID +" " +height );
}



//////////////////////////////////////////////////
//��`�E�B���h�E�\��

public void judgeFontAttribute()
{
	int iHeight;

	//�t�H���g�T�C�Y����t�H���g�^�C�v����
	Font fon = new Font( "TimesRoman", Font.PLAIN, 20 );
	FontMetrics fm = getFontMetrics( fon );
	iHeight = fm.getAscent();
	if( iHeight > 18 ) g_bJREFont = true;
	System.out.println( "Font = " +iHeight );
}


public void drawHalfString( Graphics g, String szStr, int iFontSize, int x, int y, int iXsize, int iYsize )
{
	int x2, y2;
	int xs2, ys2;
	Font fon;

	//JavaVM��JRE�Ńt�H���g��ς���
	if( g_bJREFont == true ) fon = new Font( "TimesRoman", Font.PLAIN, iFontSize );
	else fon = new Font( "TimesRoman", Font.BOLD, iFontSize );

	FontMetrics fm = getFontMetrics( fon );
	g.setFont( fon );
	//g.setColor( Color.black );

	xs2 = fm.stringWidth( szStr );
	ys2 = fm.getAscent();
	if( ys2 >= 18 ){
		ys2 -= 6;
		if( g_bJREFont == false ) ++ys2;
	} else if( ys2 >= 16 ) ys2 -= 4;
	else if( ys2 >= 12 ) ys2 -= 2;
	if( g_bJREFont == false ) ++ys2;

	//����������\���ʒu����
	x2 = x +(iXsize -xs2) /2;
	y2 = y +(iYsize +ys2) /2;
	g.setColor( new Color(160,128,96) );
	g.drawString( szStr , x2+1, y2 );
	g.drawString( szStr , x2+1, y2+1 );
	g.setColor( Color.black );
	g.drawString( szStr , x2, y2 );
	//System.out.println( x2 +" " +y2 +" " +xs2 +" " +ys2 +" " );
}


public void displayPushButton( int iYesno, String szStr, int iFontSize, int x, int y, int iXsize, int iYsize )
{
	if( yesnoNumber == iYesno ){
		gBuffStatus.drawImage( imgBuffButton, 0, y, this );
		drawHalfString( gBuffStatus, szStr, iFontSize, x+3, y+3, iXsize, iYsize );
	} else {
		drawHalfString( gBuffStatus, szStr, iFontSize, x, y, iXsize, iYsize );
	}
}


public void displayConfigWindow( Graphics g, boolean flag, boolean flag1, boolean flag2, boolean flag3 )
{
	//�ϐ���`
	int x;
	int i, j, k;
	int numberCrop;
	int number;
	Font fon;
	int iAddPos[] = new int[4];

	if( flag == false ){
		g.drawImage( imgBuffStatus, 40*11, 0, this );
		return;
	}
	//�R�}���h�E�B���h�E
	if( flag3 == true ){
		//fon = new Font("Courier", Font.BOLD, 18 );
		fon = new Font("TimesRoman", Font.BOLD, 18 );
		gBuffStatus.setFont( fon );
		
		gBuffStatus.setColor( Color.gray );
		gBuffStatus.fillRect( 0, 0, 40*3, 40*11 );
		
		//�g�`��
		for( i = 0 ; i < 4 ; ++i ){ 
			for( j = 0 ; j < 3 ; ++j ) gBuffStatus.drawImage( imgCrop[j+CROP_STFRAME], j*40, 300+i*35, this );
		}
		//�����{�^���쐬
		gBuffButton.setColor( Color.black );
		gBuffButton.fillRect( 0, 0, 120, 35 );
		for( j = 0 ; j < 3 ; ++j ) gBuffButton.drawImage( imgCrop[j+CROP_STFRAME], j*40+3, 0+3, this );
		
		//�e�{�^����`��
		if( quickSaveFlag == true ) displayPushButton( YESNO_QLOAD, "Quick Load", 18, 0, 300, 40*3, 35 );
		else displayPushButton( YESNO_TEXTLOAD, "Password", 18, 0, 300, 40*3, 35 );
		
		displayPushButton( YESNO_QSAVE, "Quick Save", 18, 0, 300+35, 40*3, 35 );
		displayPushButton( YESNO_RESTART, "Restart Game", 16, 0, 300+2*35, 40*3, 35 );
		displayPushButton( YESNO_WWAH, "Goto WWA", 18, 0, 300+3*35, 40*3, 35 );
	}
	//�X�e�[�^�X�E�B���h�E
	if( flag1 == true ){
		//�����͏��
		if( (statusEnergyMax != 0) && (statusEnergy > statusEnergyMax) ) statusEnergy = statusEnergyMax;
		
		//�g�`��
		for( i = 0 ; i < 4 ; ++i ){ 
			if( (g_iChangeStatus[i] == 0) || (g_bAnmItem == 0) ){
				for( j = 0 ; j < 3 ; ++j ) gBuffStatus.drawImage( imgCrop[j+CROP_STFRAME], j*40, i*35, this );
			} else {
				//�����{�^��
				gBuffStatus.drawImage( imgBuffButton, 0, i*35, this );
			}
		}
		for( i = 0 ; i < 4 ; ++i ){ 
			if( (g_iChangeStatus[i] > 0) && (g_bAnmItem == 1) ) iAddPos[i] = 3;
			else iAddPos[i] = 0;
		}
		//�C���[�W�̕\��
		gBuffStatus.drawImage( imgCrop[CROP_ENERGY], FRAME_ENERGY_X -40*11 +iAddPos[0], FRAME_ENERGY_Y +iAddPos[0], this );
		gBuffStatus.drawImage( imgCrop[CROP_STRENGTH], FRAME_STRENGTH_X -40*11 +iAddPos[1], FRAME_STRENGTH_Y +iAddPos[1], this );
		gBuffStatus.drawImage( imgCrop[CROP_DEFENCE], FRAME_DEFENCE_X -40*11 +iAddPos[2], FRAME_DEFENCE_Y +iAddPos[2], this );
		gBuffStatus.drawImage( imgCrop[CROP_GOLD], FRAME_GOLD_X -40*11 +iAddPos[3], FRAME_GOLD_Y +iAddPos[3], this );
		
		//�p�����[�^�\��
		if( statusEnergy < 0 ) statusEnergy = 0;
		drawHalfString( gBuffStatus, String.valueOf(statusEnergy), 22, 38 +iAddPos[0], 0 +iAddPos[0], 40*3-38, 35 );
		drawHalfString( gBuffStatus, String.valueOf(statusStrength +itemStrength), 22, 38 +iAddPos[1], 35 +iAddPos[1], 40*3-38, 35 );
		drawHalfString( gBuffStatus, String.valueOf(statusDefence +itemDefence), 22, 38 +iAddPos[2], 35*2 +iAddPos[2], 40*3-38, 35 );
		drawHalfString( gBuffStatus, String.valueOf(statusGold), 22, 38 +iAddPos[3], 35*3 +iAddPos[3], 40*3-38, 35 );
	}
	//�A�C�e���E�B���h�E
	int iPlus;
	int iBox;
	if( flag2 == true ){
		for( i = 0 ; i < 4 ; ++i ){
			for( j = 0 ; j < 3 ; ++j ){
				iBox = i*3+j;
				gBuffStatus.drawImage( imgCrop[CROP_ITEMFRAME], j*40, 140+i*40, this );
				
				if( (g_iAnmItemCount >= 1) && (g_iAnmItemCount <= (15 +g_iAnmItemCountAdd)) && (g_iAnmItemBox == iBox) && (g_iAnmItemOld == 0) ) continue;
				
				if( (itemBox[iBox] != 0) || ((iBox == itemboxBuff) && (g_iUseItem > 0)) ){
					if( (iBox == itemboxBuff) && (g_iUseItem > 0) ) numberCrop = objectAttribute[g_iUseItem][ATR_CROP1];
					else numberCrop = objectAttribute[itemBox[iBox]][ATR_CROP1];
					//�A�C�e���A�j����
					if( (g_iAnmItemBox == iBox) && (g_iAnmItemOld != 0) && (g_iAnmItemCount != 0) ) numberCrop = objectAttribute[g_iAnmItemOld][ATR_CROP1];
					
					//�A�C�e���`��
					if( (objectAttribute[itemBox[iBox]][ATR_MODE] == 0) && (iBox != itemboxBuff) ){
						gBuffStatus.drawImage( imgCrop[numberCrop], j*40, 140+i*40, this );
					}
					//�g�p�^�A�C�e���͕ʕ`��
					else {
						iPlus = 0;
						if( (g_iUseItem > 0) && (iBox == itemboxBuff) ){
							//�����{�^���쐬
							gBuffMap.setColor( Color.black );
							gBuffMap.fillRect( 0, 0, 40, 40 );
							iPlus = 3;
						}
						gBuffMap.drawImage( imgCrop[CROP_ITEMFRAME], iPlus, iPlus, this );
						
						//�Ԙg�`��
						if( g_iImgClickItem != 0 ){
							//���[�U�[�w��g
							gBuffMap.drawImage( imgCrop[g_iImgClickItem], iPlus, iPlus, this );
						} else {
							for( k = 0 ; k < 5 ; ++k ){
								if( (k %2) == 1 ) gBuffMap.setColor( Color.white );
								else gBuffMap.setColor( Color.red );
								gBuffMap.drawRect( k+1+iPlus, k+1+iPlus, 37-k*2, 37-k*2 );
							}
						}
						gBuffMap.drawImage( imgCrop[numberCrop], iPlus, iPlus, this );
						gBuffStatus.drawImage( imgBuffMap, j*40, 140+i*40, this );
					}
				}
			}
		}
	}
	g.drawImage( imgBuffStatus, 40*11, 0, this );
}



//////////////////////////////////////////////////
//�A�C�e���̕��ׂ���

public void arrangeItem( int data )
{
	//�ϐ���`
	int i;
	int dataOld;
	int iOldItemStrength, iOldItemDefence;

	for( i = 0 ; i < 12 ; ++i ){
		if( itemBox[i] == 0 ) break;
	}
	if( (i == 12) && (data != 0) && (objectAttribute[data][ATR_NUMBER] == 0) ) return;

	if( data != 0 ){
		if( objectAttribute[data][ATR_NUMBER] != 0 ){
			dataOld = itemBox[objectAttribute[data][ATR_NUMBER] -1];
			itemBox[objectAttribute[data][ATR_NUMBER] -1] = data;
			
			if( objectAttribute[dataOld][ATR_NUMBER] != objectAttribute[data][ATR_NUMBER] ){
				for( i = 0 ; i < 12 ; ++i ){
					if( itemBox[i] == 0 ){
						itemBox[i] = dataOld;
						break;
					}
				}
			}
		} else {
			for( i = 0 ; i < 12 ; ++i ){
				if( itemBox[i] == 0 ){
					itemBox[i] = data;
					break;
				}
			}
		}
	}
	//�X�e�[�^�X�Čv�Z
	itemStrength = 0;
	itemDefence = 0;
	for( i = 0 ; i < 12 ; ++i ){
		if( itemBox[i] != 0 ){
			itemStrength += objectAttribute[itemBox[i]][ATR_STRENGTH];
			itemDefence += objectAttribute[itemBox[i]][ATR_DEFENCE];
		}
	}
}



//////////////////////////////////////////////////
//�����X�^�[�Ƃ̐퓬����

public void attackMonster( Graphics g, int Xpoint, int Ypoint )
{
	//�ϐ���`
	int i, j;
	int x, y;
	int MonsterData = mapObject[Ypoint][Xpoint];

	x = (charaX /5) *40;
	y = (charaY /5) *40;

	if( attackFlagTurn == true ){
		//���ʉ�
		if( (soundFlag == true) && (g_iAttackTurn <= 40) && ((g_iAttackTurn == 0) || (TimerCount > 10)) ) playAudio( 3 );
		
		//�L�����N�^�`��
		cropID = mapAttribute[map[playerY][playerX]][ATR_CROP1];
		gBuffMap.drawImage( imgCrop[cropID], 0, 0, this );
		if( g_bDelPlayer == 0 ) gBuffMap.drawImage( imgCrop[cropIDchara], 0, 0, this );
		paintFrame2( gBuffMap, x /40, y /40, 0, 0 );
		g.drawImage( imgBuffMap, x, y, this );
		
		//�����X�^�[�`��
		cropID = mapAttribute[map[Ypoint][Xpoint]][ATR_CROP1];
		gBuffMap.drawImage( imgCrop[cropID], 0, 0, this );
		attackMonsterSub2( g, Xpoint, Ypoint, true );
		g.drawImage( imgBuffMap, (Xpoint -mapX *10) *40, (Ypoint -mapY *10) *40, this );
		
		//�v���[���[�̍U��
		if( (statusStrength +itemStrength > monsterDefence) || (statusDefence +itemDefence < monsterStrength) || (g_bAttackJudgeMes == false) ){
			if(monsterEnergy > (statusStrength +itemStrength -monsterDefence)){
				if( (statusStrength +itemStrength) > monsterDefence ) monsterEnergy = monsterEnergy -(statusStrength +itemStrength -monsterDefence);
			} else {
				//�v���[���[�̏���
				monsterEnergy = 0;
				mdata = mapObject[Ypoint][Xpoint];
				mapFlagAll = true;
				mapFlagErase = false;
				waitCounterLast = 200;
				//�g�������X�^�[�L�����N�^�̏ꍇ
				messageFlag = true;
				strNumber = objectAttribute[mdata][ATR_STRING];
				//�L�����N�^�o��
				appearChara( g, Xpoint, Ypoint, 0 );
				statusGold += monsterGold;
				flagDisplayStatus = true;
				//�����A�C�e���o��
				if( mapObject[Ypoint][Xpoint] == mdata ){
					mapObject[Ypoint][Xpoint] = (short)objectAttribute[mdata][ATR_ITEM];
					displayCharacter( g, Xpoint, Ypoint );
				}
				attackFlag = false;
				movingSkip = 1;
				//�T�E���h
				if((objectAttribute[mdata][ATR_SOUND] != 0) && (soundFlag == true)){
					waitCounterLast = 0;
					twait( 200 );
					playAudio( objectAttribute[mdata][ATR_SOUND] );
				}
			}
		} else {
			mapFlagAll = true;
			mapFlagErase = false;
			messageFlag = true;
			strNumber = 1;
			strMessage[strNumber] = "����̖h��\�͂���������I";
			attackFlag = false;
			movingSkip = 1;
			waitCounterLast = 200;
		}
		displayMonsterStatus( g, MonsterData );
		attackFlagTurn = false;
		
		if( (g_iAttackTurn > 40) || (TimerCount <= 10) ) twait( 20 );
		else twait( 120 );
		++g_iAttackTurn;
	}
	else if( attackFlagTurn == false ){
		//���ʉ�
		//if( (soundFlag == true) && (g_iAttackTurn <= 40) ) audio[4].play();
		
		//�����X�^�[�`��
		cropID = mapAttribute[map[Ypoint][Xpoint]][ATR_CROP1];
		gBuffMap.drawImage( imgCrop[cropID], 0, 0, this );
		attackMonsterSub2( g, Xpoint, Ypoint, false );
		g.drawImage( imgBuffMap, (Xpoint -mapX *10) *40, (Ypoint -mapY *10) *40, this );
		
		//�L�����N�^�`��
		cropID = mapAttribute[map[playerY][playerX]][ATR_CROP1];
		gBuffMap.drawImage( imgCrop[cropID], 0, 0, this );
		if( g_bDelPlayer == 0 ) gBuffMap.drawImage( imgCrop[cropIDchara], 0, 0, this );
		gBuffMap.drawImage( imgCrop[CROP_BOM], 0, 0, this);
		paintFrame2( gBuffMap, x /40, y /40, 0, 0 );
		g.drawImage( imgBuffMap, x, y, this );
		
		//�����X�^�[�̍U��
		if( monsterStrength > statusDefence +itemDefence ){
			if(statusEnergy > (monsterStrength-statusDefence -itemDefence)){
				statusEnergy = statusEnergy - (monsterStrength -statusDefence -itemDefence);
			} else {
				//�����X�^�[�̏���
				JumpPoint( gameoverXp, gameoverYp );
				
				statusEnergy = 0;
				mapFlagAll = true;
				mapFlagErase = true;
				attackFlag = false;
				twait( 800 );
			}
		}
		attackFlagTurn = true;
		
		if( (g_iAttackTurn > 40) || (TimerCount <= 10) ) twait( 20 );
		else twait( 120 );
		++g_iAttackTurn;
	}
	//�X�e�[�^�X�\��
	displayConfigWindow( g, true, true, true, false );
}


public void attackMonsterSub2( Graphics g, int Xpoint, int Ypoint, boolean flag )
{
	//�ϐ���`
	int x, y;
	
	cropID = objectAttribute[mapObject[Ypoint][Xpoint]][ATR_CROP1];
	x = (Xpoint -mapX *10) *40;
	y = (Ypoint -mapY *10) *40;
	gBuffMap.drawImage( imgCrop[cropID], 0, 0, this );
	if( flag == true ) gBuffMap.drawImage( imgCrop[CROP_BOM], 0, 0, this);
	
	//�t���[���`��
	paintFrame2( gBuffMap, x /40, y /40, 0, 0 );
}


//�����X�^�[�̃X�e�[�^�X�\��
public void displayMonsterStatus( Graphics g, int MonsterData )
{
	int Ytop;
	if( charaY /5 >= 5 ) Ytop = 80;
	else Ytop = 300;

	gBuffBattle.setColor( new Color(96,96,96) );
	gBuffBattle.fillRect( 0, 0, 340, 60 );
	gBuffBattle.setColor( Color.white );
	gBuffBattle.fillRoundRect( 2, 2, 340-4, 60-4, 20, 20 );

	//�t�H���g�ݒ�
	Font fon = new Font( "Courier", Font.PLAIN, 18 );
	gBuffBattle.setFont( fon );

	cropID = objectAttribute[MonsterData][ATR_CROP1];
	gBuffBattle.drawImage( imgCrop[cropID], 20, 10, this );

	drawStringBold( gBuffBattle, "������  "+monsterEnergy, Color.black, Color.gray, 80, 26 );
	drawStringBold( gBuffBattle, "�U����  "+monsterStrength +"  �h���  "+monsterDefence, Color.black, Color.gray, 80, 46 );
	g.drawImage( imgBuffBattle, 50, Ytop, this );
}



//////////////////////////////////////////////////
//�w��ʒu�̃}�b�v��\��

public void displayCharacter( Graphics g, int Xpoint, int Ypoint )
{
	//�ϐ���`
	int i, j;
	int x, y;
	int mapData;

	//�����_���L�����N�^
	RandomCharacter( Ypoint, Xpoint );

	if( !((Xpoint >= mapX *10) && (Xpoint < mapX *10 +11) && (Ypoint >= mapY *10) && (Ypoint < mapY *10 +11)) ) return;
	if( mapFlagAll == true ) return;
	if( g_bSkipPaint == true ) return;

	//�w�i�F�œh��Ԃ�
	gBuffMap.setColor( Color.gray );
	gBuffMap.fillRect( 0, 0, 40, 40 );

	//�w�i�`��
	mapData = map[Ypoint][Xpoint];
	cropID = mapAttribute[mapData][ATR_CROP1];
	gBuffMap.drawImage( imgCrop[cropID], 0, 0, this );

	//�v���[���[�`��
	if( (g_bDelPlayer == 0) && (Xpoint == (charaX /5 +mapX *10)) && (Ypoint == (charaY /5 +mapY *10)) ) gBuffMap.drawImage( imgCrop[cropIDchara],0,0,this );

	x = (Xpoint %10) *40;
	if( (Xpoint /10) != mapX ) x = 40 *10;
	y = (Ypoint %10) *40;
	if( (Ypoint /10) != mapY ) y = 40 *10;

	//�I�u�W�F�N�g�`��
	mapData = mapObject[Ypoint][Xpoint];
	//�v���[���[�Ǝ擾�^�A�C�e�����d�Ȃ�ꍇ�͕`�悵�Ȃ�
	if( (mapData != 0) && (CheckNoDrawParts(mapData,Xpoint,playerX,Ypoint,playerY) == false) ){
		cropID = objectAttribute[mapData][ATR_CROP1];
		gBuffMap.drawImage( imgCrop[cropID], 0, 0, this );
	}
	//�t���[���`��
	paintFrame2( gBuffMap, x /40, y /40, 0, 0 );
	g.drawImage( imgBuffMap, x, y, this );
}



//////////////////////////////////////////////////
//�X�R�A�\��

public void displayScore( Graphics g )
{
	//�ϐ�
	int i;

	Font fon = new Font("Monospaced", Font.BOLD, 22 );
	g.setFont( fon );

	g.setColor( Color.white );
	g.fillRoundRect( 40*2, 30, 40*7, 40, 8, 8 );

	g.setColor( Color.black );
	g.drawString( "Score", 40*3, 60 -2 );
	g.drawString( String.valueOf( score ), 40*6, 60 -2 );
}



//////////////////////////////////////////////////
//�g���o���L�����N�^

public void appearChara( Graphics g, int Xpoint, int Ypoint, int flagNumber )
{
	appearChara( g, Xpoint, Ypoint, flagNumber, 0 );
}

public void appearChara( Graphics g, int Xpoint, int Ypoint, int flagNumber, int iMapNumber )
{
	int i;
	int dataChara;
	int dataMode;
	int x, y;
	int mapNumber;
	boolean flag;
	int iMin = 0;
	int iMax = 10;

	if( flagNumber == 1 ){
		mapNumber = map[Ypoint][Xpoint];
		flag = false;
	} else if( flagNumber == 2 ){
		mapNumber = itemBox[itemboxBuff];
		flag = true;
	} else {
		mapNumber = mapObject[Ypoint][Xpoint];
		flag = true;
	}
	if( iMapNumber != 0 ) mapNumber = iMapNumber;

	//��ґ���̏ꍇ
	if( flagNumber == 3 ){
		iMax = 5;
	} else if( flagNumber == 4 ){
		iMin = 5;
		iMax = 10;
	}

	//�g���L�����N�^�E�f�[�^�R���o�[�g
	for( i = iMin ; i < iMax ; ++i ){
		if( flag == true ) dataChara = objectAttribute[mapNumber][20+i*4];
		else dataChara = mapAttribute[mapNumber][20+i*4];
		
		if( flag == true ) dataMode = objectAttribute[mapNumber][20+i*4+3];
		else dataMode = mapAttribute[mapNumber][20+i*4+3];
		
		//�w�x���W�n
		if( flag == true ) x = objectAttribute[mapNumber][20+i*4+1];
		else x = mapAttribute[mapNumber][20+i*4+1];
		//�v���[���[�ʒu�w��
		if( x == 9000 ) x = playerX;
		else if( x > 9000 ) x = x - 10000 + Xpoint;
		
		if( flag == true ) y = objectAttribute[mapNumber][20+i*4+2];
		else y = mapAttribute[mapNumber][20+i*4+2];
		//�v���[���[�ʒu�w��
		if( y == 9000 ) y = playerY;
		else if( y > 9000 ) y = y - 10000 + Ypoint;
		
		if( (x == 0) && (y == 0) ) continue;
		
		if( (x >= 0) && (x <= g_iMapWidth-1) && (y >= 0) && (y <= g_iMapWidth-1) ){
			if( dataMode == 0 ){
				if( (dataChara >= g_iObjectPartsMax) ) continue;
				mapObject[y][x] = (short)dataChara;
				displayCharacter( g, x, y );
			} else if( dataMode == 1 ){
				if( (dataChara >= g_iMapPartsMax) ) continue;
				map[y][x] = (short)dataChara;
				displayCharacter( g, x, y );
			}
		}
	}
}



//////////////////////////////////////////////////
//�t�q�k�W�����v

String oldString;

public void jumpURL( String str, boolean returnFlag )
{
	int i;
	URL url;
	String urlStr;
	String target = "";
	boolean flag = false;
	String encodeName = URLEncoder.encode( worldName );
	int idNumber;
	int idNumberB;

	StringTokenizer strtoken = new StringTokenizer( str );
	urlStr = strtoken.nextToken();
	if( strtoken.hasMoreTokens() ){
		target = strtoken.nextToken();
	}
	if( (returnFlag == true) && (!strMessage[5].equals("BLANK")) ){
		yesnoNumber = YESNO_URLGATE;
		yesnoFlag = true;
		return;
	}
	//CGI����p
	String adr = new String( urlStr.toCharArray(),urlStr.length() -3,3 );

	//�Ïؔԍ��v�Z
	idNumber = statusEnergy *7;
	idNumber += (statusStrength +itemStrength) *11;
	idNumber += (statusDefence +itemDefence) *19;
	idNumber += statusGold *5;
	idNumber += playerX *17;
	idNumber += playerY *21;
	for( i = 0 ; i < 12 ; ++i ) idNumber += itemBox[i] *17;
	idNumber = idNumber %9999;
	if( parse(worldPassword) != 0 ) idNumber *= worldPassNumber;
	idNumber = idNumber %9999;
	//�ړ��񐔐V�K�ǉ�
	idNumberB = g_iStep *231;
	for( i = 0 ; i < 100 ; ++i ) idNumberB += g_iValiable[i] *(i +5);
	if( parse(worldPassword) != 0 ) idNumberB *= worldPassNumber;
	idNumberB = idNumber %9999;

	//�g�����M
	if( target.equals("EXPAND") ){
		urlStr = urlStr +"&";
		flag = true;
	} else if( adr.equals("cgi") ){
		urlStr = urlStr +"?";
		flag = true;
	}
	if( flag == true ){
		//�Q�d���M�̖h�~
		//if( urlStr.equals(oldString) ) return;
		oldString = urlStr;
		//CGI�ő��M����f�[�^
		urlStr = urlStr +"HP=" +statusEnergy +"&AT=" +(statusStrength +itemStrength) +"&DF=" +(statusDefence +itemDefence) +"&GD=" +statusGold +"&PX=" +playerX +"&PY=" +playerY +"&ID=" +idNumber +"&WNAME=" +encodeName;
		for( i = 0 ; i < 12 ; ++i ) urlStr = urlStr +"&ITEM" +(i+1) +"=" +itemBox[i];
		// if( !target.equals("EXPAND") ) urlStr = urlStr +"&FLAG=ON" +"&DAT=" +getParameter("paramMapName");
		if( !target.equals("EXPAND") ) urlStr = urlStr +"&FLAG=ON" +"&DAT=" +mapName;
		urlStr = urlStr +"&STEP=" +g_iStep +"&IDB=" +idNumberB;
	}
	System.out.println( urlStr );

	//���������
	if( g_bUseUrlJump == true ) System.gc();

	try{
		urlJumpFlag = true;
		g_bUseUrlJump = true;
		//�^�[�Q�b�g�̗L���ʂɂt�q�k�W�����v
		if( target.equals("BLANK") || target.equals("EXPAND") ){
			url = new URL( getDocumentBase(), urlStr );
			getAppletContext().showDocument( url );
		} else {
			if( g_bPopup == true ) target = "wwawindow";
			if( target.equals("") ){
				url = new URL( getDocumentBase(), urlStr );
				getAppletContext().showDocument( url );
			} else {
				url = new URL( getDocumentBase(), urlStr );
				getAppletContext().showDocument( url, target );
			}
		}
	} catch( MalformedURLException e ){
		System.err.println( "140 URL Error!" );
	}
}



//////////////////////////////////////////////////
//���b�Z�[�W�̕\��

public void DisplayMessage( String str, boolean bCenter )
{
	messageFlag = true;
	strNumber = 1;
	strMessage[strNumber] = str;
	g_bDisplayCenter = bCenter;
}



//////////////////////////////////////////////////
//�f�[�^�̈ꎞ�ۑ�

public void QuickSave()
{
	int i, j;
	int x, y;
	int point = 0;

	QSaveParameter[point] = charaX;
	QSaveParameter[++point] = mapX;
	QSaveParameter[++point] = charaY;
	QSaveParameter[++point] = mapY;
	QSaveParameter[++point] = statusEnergy;
	QSaveParameter[++point] = statusStrength;
	QSaveParameter[++point] = statusDefence;
	QSaveParameter[++point] = statusGold;
	QSaveParameter[++point] = gameoverXp;
	QSaveParameter[++point] = gameoverYp;
	QSaveParameter[++point] = itemStrength;
	QSaveParameter[++point] = itemDefence;
	QSaveParameter[++point] = g_iImgCharaCrop;
	QSaveParameter[++point] = CROP_YES;
	QSaveParameter[++point] = g_bSaveStop;
	QSaveParameter[++point] = g_bDefault;
	QSaveParameter[++point] = g_bOldMap;
	QSaveParameter[++point] = g_iStep;
	QSaveParameter[++point] = g_iMusicNumber;
	QSaveParameter[++point] = statusEnergyMax;
	for( i = 0 ; i < 12 ; ++i ) QSaveParameter[++point] = itemBox[i];
	QSaveParameter[++point] = CROP_ENERGY;
	QSaveParameter[++point] = CROP_STRENGTH;
	QSaveParameter[++point] = CROP_DEFENCE;
	QSaveParameter[++point] = CROP_GOLD;
	QSaveParameter[++point] = CROP_BOM;
	QSaveParameter[++point] = CROP_STFRAME;
	QSaveParameter[++point] = CROP_ITEMFRAME;
	QSaveParameter[++point] = CROP_MAINFRAME;
	QSaveParameter[++point] = g_bDelPlayer;
	QSaveParameter[++point] = g_iImgClickItem;
	QSaveParameter[++point] = g_iEffWait;
	QSaveParameter[++point] = g_bAnmItem;
	for( i = 0 ; i < 4 ; ++i ) QSaveParameter[++point] = g_iEffCrop[i];

	//�ϐ��p
	for( i = 0 ; i < 100 ; ++i ) QSaveParameter[++point] = g_iValiable[i];

	//�}�b�v�f�[�^
	for( x = 0 ; x < g_iMapWidth ; ++x ){
		for( y = 0 ; y < g_iMapWidth ; ++y ){
			QSaveMap[x][y] = map[x][y];
			QSaveMapObject[x][y] = mapObject[x][y];
		}
	}
}


public void QuickLoad()
{
	int i, j;
	int x, y;
	int point = 0;

	//�w�i���y��~
	playAudio( 99 );
	g_bRestPlayer = true;

	charaX = QSaveParameter[point];
	charaX = charaX -(charaX %5);
	mapX = QSaveParameter[++point];
	charaY = QSaveParameter[++point];
	charaY = charaY -(charaY %5);
	mapY = QSaveParameter[++point];
	statusEnergy = QSaveParameter[++point];
	statusStrength = QSaveParameter[++point];
	statusDefence = QSaveParameter[++point];
	statusGold = QSaveParameter[++point];
	gameoverXp = QSaveParameter[++point];
	gameoverYp = QSaveParameter[++point];
	itemStrength = QSaveParameter[++point];
	itemDefence = QSaveParameter[++point];
	g_iImgCharaCrop = QSaveParameter[++point];
	SetYesNoCrop( QSaveParameter[++point] );
	g_bSaveStop = QSaveParameter[++point];
	g_bDefault = QSaveParameter[++point];
	g_bOldMap = QSaveParameter[++point];
	g_iStep = QSaveParameter[++point];
	g_iMusicNumber = QSaveParameter[++point];
	statusEnergyMax = QSaveParameter[++point];
	for( i = 0 ; i < 12 ; ++i ) itemBox[i] = QSaveParameter[++point];
	CROP_ENERGY = QSaveParameter[++point];
	CROP_STRENGTH = QSaveParameter[++point];
	CROP_DEFENCE = QSaveParameter[++point];
	CROP_GOLD = QSaveParameter[++point];
	CROP_BOM = QSaveParameter[++point];
	CROP_STFRAME = QSaveParameter[++point];
	CROP_ITEMFRAME = QSaveParameter[++point];
	CROP_MAINFRAME = QSaveParameter[++point];
	g_bDelPlayer = QSaveParameter[++point];
	g_iImgClickItem = QSaveParameter[++point];
	g_iEffWait = QSaveParameter[++point];
	g_bAnmItem = QSaveParameter[++point];
	for( i = 0 ; i < 4 ; ++i ) g_iEffCrop[i] = QSaveParameter[++point];
	//�ϐ��p
	for( i = 0 ; i < 100 ; ++i ) g_iValiable[i] = QSaveParameter[++point];

	//�w�i���y�ĊJ
	playAudio( 100 );

	//�}�b�v�f�[�^
	for( x = 0 ; x < g_iMapWidth ; ++x ){
		for( y = 0 ; y < g_iMapWidth ; ++y ){
			map[x][y] = QSaveMap[x][y];
			mapObject[x][y] = QSaveMapObject[x][y];
		}
	}
	SetDirectPlayer( 2 );

	//��ʏ�������
	g_bFadeBlack = true;
}



//////////////////////////////////////////////////
// �Q�[���I�[�o�[

public void GameOver( Graphics g )
{
	displayConfigWindow( g, true, true, true, false );

	mapX = gameoverXp /10;
	charaX = (gameoverXp %10) *5;
	mapY = gameoverYp /10;
	charaY = (gameoverYp %10) *5;
	statusEnergy = 0;

	mapFlagAll = true;
	mapFlagErase = true;
	g_bGameOver = false;

	twait( 500 );
}



//////////////////////////////////////////////////
//�������L�����N�^�̐ݒ�

public void WonderingChara()
{
	int i, j;
	int x, y;
	int xp, yp;
	int xc, yc;
	boolean FlagMatrix[][] = new boolean[13][13];
	boolean flag;
	Random random = new Random();
	int xold, yold;

	//���݈ʒu���W�����v�Q�[�g�Ȃ�X�L�b�v
	if( mapAttribute[map[playerY][playerX]][ATR_TYPE] == MAP_LOCALGATE ) return;
	if( objectAttribute[mapObject[playerY][playerX]][ATR_TYPE] == OBJECT_LOCALGATE ) return;

	//�ړ��񐔉��Z
	if( g_iTurnSkip == 0 ) ++g_iStep;
	g_bRestPlayer = false;

	for( x = 0 ; x < 13 ; ++x ){
		for( y = 0 ; y < 13 ; ++y ){
			FlagMatrix[x][y] = false;
		}
	}
	for( x = -1 ; x < 12 ; ++x ){
		for( y = -1 ; y < 12 ; ++y ){
			if( FlagMatrix[x+1][y+1] == true ) continue;
			if( (mapX*10+x < 0) || (mapY*10+y < 0) || (mapX*10+x > g_iMapWidth-1) || (mapY*10+y > g_iMapWidth-1) ) continue;
			
			mdata = mapObject[y +mapY *10][x +mapX *10];
			if( (mdata != 0) && (objectAttribute[mdata][ATR_MOVE] > 0) && (objectAttribute[mdata][ATR_MOVE] <= 3) && (objectAttribute[mdata][ATR_TYPE] != OBJECT_LOCALGATE) ){
				flag = true;
				
				//�v���[���[�ʒu
				xp = charaX /5;
				yp = charaY /5;
				if( charaX %5 == 1 ) ++xp;
				if( charaY %5 == 1 ) ++yp;
				
				//�΂߈ړ�
				xc = x;
				if( objectAttribute[mdata][ATR_MOVE] == 1 ){
					if( x > xp ) xc = x -1;
					else if( x < xp ) xc = x +1;
				} else if( objectAttribute[mdata][ATR_MOVE] == 2 ){
					if( x > xp ) xc = x +1;
					else if( x < xp ) xc = x -1;
				}
				yc = y;
				if( objectAttribute[mdata][ATR_MOVE] == 1 ){
					if( y > yp ) yc = y -1;
					else if( y < yp ) yc = y +1;
				} else if( objectAttribute[mdata][ATR_MOVE] == 2 ){
					if( y > yp ) yc = y +1;
					else if( y < yp ) yc = y -1;
				}
				xold = xc;
				yold = yc;
				//�΂߈ړ��s�̏ꍇ
				if( WonderingCharaSub(xc,yc,xp,yp) == false ){
					if( (((moveDirect == 2) || (moveDirect == 8)) && (objectAttribute[mdata][ATR_TYPE] == OBJECT_MONSTER))
								|| (((moveDirect == 4) || (moveDirect == 6)) && (objectAttribute[mdata][ATR_TYPE] != OBJECT_MONSTER)) ){
						if( WonderingCharaSub(x,yc,xp,yp) == false ){
							if( WonderingCharaSub(xc,y,xp,yp) == false ) flag = false;
							else yc = y;
						} else {
							xc = x;
						}
					} else {
						if( WonderingCharaSub(xc,y,xp,yp) == false ){
							if( WonderingCharaSub(x,yc,xp,yp) == false ) flag = false;
							else xc = x;
						} else {
							yc = y;
						}
					}
				}
				//�ǔ������őO���s�Ȃ�΂ߑO���A���_�ɉ������A�����鑮���Ō���s�Ȃ�΂ߌ���A���_�ɉ�����
				if( (objectAttribute[mdata][ATR_MOVE] == 1) || (objectAttribute[mdata][ATR_MOVE] == 2) ){
					j = 1;
					if( objectAttribute[mdata][ATR_MOVE] == 1 ) j = -1;
					if( (xp != x) && (flag == false) ){
						xc = xold;
						yc = y +1 *j;
						if( WonderingCharaSub(xc,yc,xp,yp) == false ){
							yc = y -1 *j;
							//����������
							if( WonderingCharaSub(xc,yc,xp,yp) == false ){
								xc = x;
								yc = y +1 *j;
								if( WonderingCharaSub(xc,yc,xp,yp) == false ) yc = y -1 *j;
							}
						}
					}
					flag = WonderingCharaSub( xc, yc, xp, yp );
					//�x��������
					if( (yp != y) &&  (flag == false) ){
						yc = yold;
						xc = x +1 *j;
						if( WonderingCharaSub(xc,yc,xp,yp) == false ){
							xc = x -1 *j;
							//����������
							if( WonderingCharaSub(xc,yc,xp,yp) == false ){
								yc = y;
								xc = x +1 *j;
								if( WonderingCharaSub(xc,yc,xp,yp) == false ) xc = x -1 *j;
							}
						}
					}
					flag = WonderingCharaSub( xc, yc, xp, yp );
				}
				//�����_���Ɉړ�
				if( flag == false ){
					for( i = 0 ; i < 50 ; ++i ){
						xc = x +random.nextInt() %2;
						yc = y +random.nextInt() %2;
						if( WonderingCharaSub(xc,yc,xp,yp) == true ){
							flag = true;
							break;
						}
					}
				}
				//�t���O�ƃL�����N�^�Z�b�g
				if( flag == true ){
					if( (x >= 0) && (y >= 0) && (x < 11) && (y < 11) ) mapFlag[x][y] = true;
					if( (xc >= 0) && (yc >= 0) && (xc < 11) && (yc < 11) ) mapFlag[xc][yc] = true;
					//���̐ݒu
					if( (xc >= -1) && (yc >= -1) && (xc < 12) && (yc < 12) ) FlagMatrix[xc+1][yc+1] = true;
					mapObject[y +mapY *10][x +mapX *10] = 0;
					mapObject[mapY *10 +yc][mapX *10 +xc] = (short)mdata;
					//�ړ��A�j���p�t���O
					if( (xc >= -1) && (yc >= -1) && (xc < 12) && (yc < 12) ){
						if( xc > x ) g_iMapObjMove[yc+1][xc+1][0] = -5;
						else if( xc < x ) g_iMapObjMove[yc+1][xc+1][0] = 5;
						if( yc > y ) g_iMapObjMove[yc+1][xc+1][1] = -5;
						else if( yc < y ) g_iMapObjMove[yc+1][xc+1][1] = 5;
					}
				}
			}
		}
	}
}

public boolean WonderingCharaSub( int xc, int yc, int xp, int yp )
{
	if( (mapX*10+xc < 0) || (mapY*10+yc < 0) || (mapX*10+xc > g_iMapWidth-1) || (mapY*10+yc > g_iMapWidth-1) ) return false;

	if( (mapAttribute[map[mapY *10 +yc][mapX* 10 +xc]][ATR_TYPE] == MAP_WALL)
			|| (mapObject[mapY *10 +yc][mapX *10 +xc] != 0)
				|| ((map[mapY *10 +yc][mapX *10 +xc] == 0) && (g_bOldMap == 0))
					|| ((xp == xc) && (yp == yc)) ){
		return false;
	}
	return true;
}



//////////////////////////////////////////////////
//�����X�^�[�̔\�͒l�\��

public void DisplayMonsterData( Graphics g )
{
	int i;
	int x, y;
	int objectData;
	int damage;
	int energyM;
	int attack, attackM;
	int turn;
	int number = 0;
	int oldData[] = new int[8];		//���łɌv�Z���������X�^�[

	gBuff.setColor( Color.white );
	gBuff.fillRect( 0, 0, 440, 440 );
	//�t�H���g�ݒ�
	Font fon = new Font( "Courier", Font.PLAIN, 16 );
	gBuff.setFont( fon );

	for( y = 0 ; y < 11 ; ++y ){
		for( x = 0 ; x < 11 ; ++x ){
			objectData = mapObject[y+mapY*10][x+mapX*10];
			if( objectAttribute[objectData][ATR_TYPE] == OBJECT_MONSTER ){
				for( i = 0 ; i < number ; ++i ){
					if( oldData[i] == objectData ) break;
				}
				if( (i != number) && (oldData[i] == objectData) ) continue;
				
				monsterEnergy = objectAttribute[objectData][ATR_ENERGY];
				monsterStrength = objectAttribute[objectData][ATR_STRENGTH];
				monsterDefence = objectAttribute[objectData][ATR_DEFENCE];
				energyM = monsterEnergy;
				
				attack = (statusStrength +itemStrength) - monsterDefence;
				attackM = monsterStrength -(statusDefence +itemDefence);
				if( attackM < 0 ) attackM = 0;
				turn = 0;
				damage = 0;
				
				if( attack > 0 ){
					while( true ){
						++turn;
						energyM -= attack;
						if( energyM <= 0 ) break;
						damage += attackM;
						if( turn > 10000 ) break;
					}
				}
				//�_���[�W�\��
				drawStringBold( gBuff, "������  "+monsterEnergy, Color.black, Color.gray, 80, 40+number*50 );
				drawStringBold( gBuff, "�U����  "+monsterStrength, Color.black, Color.gray, 200, 40+number*50 );
				drawStringBold( gBuff, "�h���  "+monsterDefence, Color.black, Color.gray, 80, 60+number*50 );
				
				if( attack > 0 ) drawStringBold( gBuff, "�\���_���[�W  " +damage, Color.red, new Color(255,128,128), 200, 60+number*50 );
				else drawStringBold( gBuff, "�U������", Color.red, new Color(255,128,128), 200, 60+number*50 );
				
				//�C���[�W�\��
				cropID = objectAttribute[objectData][ATR_CROP1];
				gBuff.drawImage( imgCrop[cropID], 20, 25 +number*50, this );
				
				oldData[number] = objectData;
				++number;
			}
			if( number >= 8 ) break;
		}
		if( number >= 8 ) break;
	}
	if( number == 0 ){
		displayMonsterFlag = false;
		inputKey = true;
	} else {
		g.drawImage( imgBuff, 0, 0, this );
	}
}



//////////////////////////////////////////////////
//�ۑ��p�p�X���[�h�̕\��

Frame g_frameWin;
Checkbox g_cbSavePassword = new Checkbox( "���̃E�B���h�E�����ɂ͂����������Ă��������B" );
Checkbox g_cbLoadPassword = new Checkbox( "�f�[�^����͂����炱���������Ă��������B" );
boolean g_bOpenFrameWin = false;
boolean g_bOpenLoadPass = false;
TextArea g_taPassText = new TextArea();

public void DisplaySavePassword( String szPassText )
{
	TextArea taPassText = new TextArea();
	TextArea taMessage = new TextArea( "���̃{�b�N�X�̃e�L�X�g���f�[�^���A�p�̃p�X���[�h�ɂȂ��Ă��܂��B\n�R�s�[���ă������Ȃǂ̃e�L�X�g�G�f�B�^�ɓ\��t���ĕۑ����Ă��������B\n�uCtrl+C�v�ŃR�s�[�A�uCtrl+V�v�œ\��t���ł��܂��B\n�R�s�[�������Ȃ�������A�I����ԂɂȂ��Ă��Ȃ��Ƃ��́A\n�uCtrl+Home�v�Ńe�L�X�g�̐擪�ɃJ�[�\���������Ă��āA\n�uShift+Ctrl+End�v�ōēx�S�̂�I�����Ă���R�s�[���Ă��������B\n�p�X���[�h�͒ʏ�A���\�s�`���S�s�ɂȂ�܂��B\n�擪�Ɂu�`�v�Ō���Ɂu�y�v�̕��������邱�Ƃ��m�F���Ă��������B", 8,100 );
	taMessage.setEditable( false );

	g_frameWin = new Frame( "�f�[�^���A�p�p�X���[�h" );
	g_frameWin.setSize( 480, 440 );

	g_frameWin.setLayout( new BorderLayout() );
	g_frameWin.add( "Center", taPassText );
	g_frameWin.add( "North", taMessage );
	g_frameWin.add( "South", g_cbSavePassword );
	g_frameWin.show();

	g_cbSavePassword.setState( false );
	taPassText.setText(szPassText);
	taPassText.selectAll();

	urlJumpFlag = true;
	g_bOpenFrameWin = true;
}


public void InputLoadPassword()
{
	TextArea taMessage = new TextArea( "���̃{�b�N�X�ɑO��̃v���C�Ŏ擾�������A�p�p�X���[�h����͂��Ă��������B\n�e�L�X�g�́ACtrtl+C�ŃR�s�[�ACtrl+V�œ\��t���ł��܂��B\n�e�L�X�g�̐擪�Ɂu�`�v�Ō���Ɂu�y�v�̕��������邱�Ƃ��m�F���Ă��������B\n�쐬�҂��}�b�v�̓��e��ύX�����ꍇ��\n����ȑO�Ɏ擾�����p�X���[�h�͎g���Ȃ��Ȃ�܂��B", 5,100 );
	taMessage.setEditable( false );

	g_frameWin = new Frame( "�f�[�^���A�p�p�X���[�h����" );
	g_frameWin.setSize( 480, 440 );

	g_frameWin.setLayout( new BorderLayout() );
	g_frameWin.add( "Center", g_taPassText );
	g_frameWin.add( "North", taMessage );
	g_frameWin.add( "South", g_cbLoadPassword );
	g_frameWin.show();

	g_cbLoadPassword.setState( false );
	g_taPassText.setText("");

	urlJumpFlag = true;
	g_bOpenLoadPass = true;
	g_bOpenFrameWin = true;
}



//////////////////////////////////////////////////
//�}�b�v�f�[�^���k

int ReturnPartsCheckNumber()
{
	int i, j;
	int iCheckData = 0;

	for( i = 0 ; i < 90 ; ++i ){
		if( (i != DATA_CHECK_PARTS) && (i != DATA_CHECK_PARTS +1) && (i != DATA_CHECK) && (i != DATA_CHECK +1) ) iCheckData += unsignedByte( PressData[i] );
	}
	for( i = 0 ; i < g_iMapPartsMax ; ++i ){
		for( j = 0 ; j < MAP_ATR_MAX ; ++j ){
			iCheckData += mapAttribute[i][j];
			iCheckData %= 32000;
		}
	}
	for( i = 0 ; i < g_iObjectPartsMax ; ++i ){
		for( j = 0 ; j < OBJECT_ATR_MAX ; ++j ){
			iCheckData += objectAttribute[i][j];
			iCheckData %= 32000;
		}
	}
	System.out.println( "check_data = " +iCheckData );

	return iCheckData;
}


void PressDataSub( int iAtr, int iData, boolean bMax )
{
	if( (bMax == true) && (iData > 65000) ) iData = 65000;
	PressData[iAtr] = (byte)iData;
	PressData[iAtr +1] = (byte)(iData >> 8);
}


void PressSaveMapData()
{
	int i, j;
	int pointer = 0;
	int counter;
	int x, y;
	int length;
	int iCheckData;
	byte BuffConvert[]; // = new byte[MEM_BLOCK];
	byte MapData[]; // = new byte[MEM_BLOCK];

	MapData = new byte[g_iMapWidth *g_iMapWidth *4];
	if( PressData == null ){
		PressData = new byte[g_iMapWidth *g_iMapWidth*4 +1000];
	}

	PressDataSub( EX_DATA_STATUS_ENERGYMAX, statusEnergyMax, true );
	PressDataSub( DATA_STATUS_ENERGY, statusEnergy, true );
	PressDataSub( DATA_STATUS_STRENGTH, statusStrength, true );
	PressDataSub( DATA_STATUS_DEFENCE, statusDefence, true );
	PressDataSub( DATA_STATUS_GOLD, statusGold, true );
	PressDataSub( EX_DATA_CHARA_X, playerX, false );
	PressDataSub( EX_DATA_CHARA_Y, playerY, false );
	PressDataSub( EX_DATA_OVER_X, gameoverXp, false );
	PressDataSub( EX_DATA_OVER_Y, gameoverYp, false );
	for( i = 0 ; i < 12 ; ++i ) PressDataSub( (EX_DATA_ITEM +i*2), itemBox[i], false );
	PressDataSub( DATA_IMG_CHARA_CROP, g_iImgCharaCrop, false );
	PressDataSub( DATA_IMG_YESNO_CROP, CROP_YES, false );
	PressDataSub( DATA_SAVE_STOP, g_bSaveStop, false );
	PressDataSub( DATA_FLAG_DEFAULT, g_bDefault, false );
	PressDataSub( DATA_FLAG_OLDMAP, g_bOldMap, false );
	PressDataSub( DATA_STEP, g_iStep, false );
	PressDataSub( DATA_MUSIC, g_iMusicNumber, false );
	PressDataSub( DATA_CROP, CROP_ENERGY, false );
	PressDataSub( DATA_CROP +2, CROP_STRENGTH, false );
	PressDataSub( DATA_CROP +4, CROP_DEFENCE, false );
	PressDataSub( DATA_CROP +6, CROP_GOLD, false );
	PressDataSub( DATA_CROP +8, CROP_BOM, false );
	PressDataSub( DATA_CROP +10, CROP_STFRAME, false );
	PressDataSub( DATA_CROP +12, CROP_ITEMFRAME, false );
	PressDataSub( DATA_CROP +14, CROP_MAINFRAME, false );
	PressDataSub( DATA_FLAG_DELP, g_bDelPlayer, false );
	PressDataSub( DATA_IMG_CLICK, g_iImgClickItem, false );
	PressDataSub( DATA_EFFECT, g_iEffWait, false );
	for( i = 0 ; i < 4 ; ++i ) PressDataSub( DATA_EFFECT +2+i*2, g_iEffCrop[i], false );
	PressDataSub( DATA_ANMITEM, g_bAnmItem, false );

	//�ϐ��p
	for( i = 0 ; i < 100 ; ++i ) PressDataSub( DATA_VALIABLE +i*2, g_iValiable[i], true );

	//�}�b�v�f�[�^
	pointer = 380;
	for( x = 0 ; x < g_iMapWidth ; ++x ){
		for( y = 0 ; y < g_iMapWidth ; ++y ){
			PressData[pointer] = (byte)map[x][y];
			PressData[pointer +1] = (byte)(map[x][y] >> 8);
			pointer += 2;
		}
	}
	for( x = 0 ; x < g_iMapWidth ; ++x ){
		for( y = 0 ; y < g_iMapWidth ; ++y ){
			PressData[pointer] = (byte)mapObject[x][y];
			PressData[pointer +1] = (byte)(mapObject[x][y] >> 8);
			pointer += 2;
		}
	}

	//�p�[�c�f�[�^�̐����l�쐬
	PressDataSub( DATA_CHECK_PARTS, ReturnPartsCheckNumber(), false );

	//����ԍ��ݒ�
	for( i = 2, iCheckData = 0 ; i < pointer ; ++i ){
		iCheckData += unsignedByte(PressData[i]) *(i %18 +1);
		iCheckData %= 32000;
	}
	PressDataSub( DATA_CHECK, iCheckData, false );

	//�}�b�v�f�[�^���k
	for( i = 0, j = 0, counter = 0 ; i < pointer ; ++i ){
		if( PressData[i] == PressData[i+1] ){
			++counter;
			if( (counter == 0xff) || (i +2 > pointer) ){
				MapData[j] = PressData[i];
				MapData[j+1] = PressData[i];
				MapData[j+2] = (byte)counter;
				j += 3;
				++i;
				counter = 0;
			}
		} else {
			if( counter == 0 ){
				MapData[j] = PressData[i];
				++j;
			} else {
				MapData[j] = PressData[i];
				MapData[j+1] = PressData[i];
				MapData[j+2] = (byte)counter;
				j += 3;
			}
			counter = 0;
		}
	}
	MapData[j] = 0;
	MapData[j+1] = 0;
	MapData[j+2] = 0;
	length = j+2;
	System.out.println( "save_pointer = " +pointer +" " +length );

	//�̈�m��
	BuffConvert = new byte[length*2+2000];
	//�n�[
	BuffConvert[0] = 'A';
	//�Q�o�C�g�n�̃e�L�X�g�ɕϊ�
	for( i = 0, j = 1, counter = 0 ; i < length ; ++i ){
		BuffConvert[j] = (byte)((MapData[i] & 0x0f) +'B');
		BuffConvert[j+1] = (byte)(((MapData[i] >> 4) & 0x0f) +'B');
		j += 2;
		//���s����
		++counter;
		if( counter >= 250 ){
			BuffConvert[j] = '\n';
			counter = 0;
			++j;
		}
	}
	//�I�[
	BuffConvert[j] = 'Z';
	BuffConvert[j+1] = '\n';

	DisplaySavePassword( new String(BuffConvert,0,j+2) );
}



//////////////////////////////////////////////////
//�}�b�v�f�[�^�W�J

public void SetYesNoCrop( int iImgYesnoCrop )
{
	CROP_YES = iImgYesnoCrop;
	CROP_NO = iImgYesnoCrop +1;
	CROP_YES2 = iImgYesnoCrop +2;
	CROP_NO2 = iImgYesnoCrop +3;
}


public int ExtractDataSub( int iData )
{
	return (unsignedByte(PressData[iData]) +unsignedByte(PressData[iData+1]) *0x100);
}


public void ExtractLoadMapData()
{
	//�ϐ���`
	int i, j, k;
	int x, y;
	int counter, maxim;
	boolean bStart = false;
	int pointer = 0;
	int iDataCharaX, iDataCharaY;
	int iCheckData, iCheckDataEx;
	boolean bTerm = false;
	int length;
	byte BuffConvert[];
	byte MapData[];

	g_bOpenLoadPass = false;
	if( PressData == null ){
		PressData = new byte[g_iMapWidth *g_iMapWidth*4 +1000];
	}

	//�e�L�X�g���o�C�g�f�[�^�ɕϊ�
	String szPassText = g_taPassText.getText();
	BuffConvert = szPassText.getBytes();
	length = szPassText.length();
	if( length == 0 ) return;
	MapData = new byte[length/2];

	//�P�o�C�g�n�f�[�^�ɕϊ�
	for( i = 0, j = 0 ; j < length ; ){
		//�I�[����
		if( BuffConvert[j] == 'Z' ){
			bTerm = true;
			break;
		}
		if( bStart == false ){
			//�n�[����
			if( BuffConvert[j] == 'A' ) bStart = true;
			++j;
			if( j > 100 ) break;
		} else {
			//���s�͖���
			if( (BuffConvert[j] == '\n') || (BuffConvert[j] == '\r') ){
				++j;
				continue;
			}
			//�f�[�^�ϊ�
			if( j >= (length-1) ) break;
			MapData[i] = (byte)((BuffConvert[j] -'B') +((BuffConvert[j+1] -'B') << 4));
			++i;
			j += 2;
		}
	}

	if( (bTerm == false) || (bStart == false) ){
		DisplayMessage( "�p�X���[�h������ł͂���܂���B\n�p�X���[�h�e�L�X�g�̐擪���u�`�v\n�Ō�����u�y�v�ɂȂ��Ă��邩\n�m�F���Ă��������B", true );
		return;
	}

	//�f�[�^��
	for( i = 0, j = 0, counter = 0 ; ; ++i ){
		if( (MapData[i] == 0) && (MapData[i+1] == 0) && (MapData[i+2] == 0) ) break;
		//�������A�����Ă���Ή𓀏���
		PressData[j] = MapData[i];
		++j;
		if( MapData[i] == MapData[i+1] ){
			maxim = unsignedByte( MapData[i+2] );
			for( counter = 0 ; counter < maxim ; ++counter ){
				PressData[j] = MapData[i];
				++j;
			}
			i += 2;
		}
	}
	System.out.println( "load_pointer = " +j +" " +i );

	pointer = j-1;
	//����ԍ�����
	iCheckDataEx = ExtractDataSub( DATA_CHECK );
	for( i = 2, iCheckData = 0 ; i < pointer ; ++i ){
		iCheckData += unsignedByte(PressData[i]) *(i %18 +1);
		iCheckData %= 32000;
	}
	if( iCheckData != iCheckDataEx ){
		DisplayMessage( "�p�X���[�h������ł͂���܂���B\n�p�X���[�h�̈ꕔ���������Ă��Ȃ����Ȃǂ��m�F���Ă��������B", true );
		return;
	}

	//�p�[�c�f�[�^�̐����l����
	iCheckDataEx = ExtractDataSub( DATA_CHECK_PARTS );
	if( ReturnPartsCheckNumber() != iCheckDataEx ){
		DisplayMessage( "���̃p�X���[�h�͂��̃}�b�v�ł�\n�g�p�ł��܂���B\n�}�b�v�̓��e���O�񂩂�ύX����Ă��邩�A�܂��͕ʂ̃}�b�v�̃p�X���[�h���g�p���Ă��܂��B", true );
		return;
	}

	//�w�i���y��~
	playAudio( 99 );
	g_bRestPlayer = true;

	statusEnergyMax = ExtractDataSub( EX_DATA_STATUS_ENERGYMAX );
	statusEnergy = ExtractDataSub( DATA_STATUS_ENERGY );
	statusStrength = ExtractDataSub( DATA_STATUS_STRENGTH );
	statusDefence = ExtractDataSub( DATA_STATUS_DEFENCE );
	statusGold = ExtractDataSub( DATA_STATUS_GOLD );
	gameoverXp = ExtractDataSub( EX_DATA_OVER_X );
	gameoverYp = ExtractDataSub( EX_DATA_OVER_Y );
	for( i = 0 ; i < 12 ; ++i ) itemBox[i] = ExtractDataSub( (EX_DATA_ITEM +i*2) );
	arrangeItem( 0 );
	iDataCharaX = ExtractDataSub( EX_DATA_CHARA_X );
	iDataCharaY = ExtractDataSub( EX_DATA_CHARA_Y );
	JumpPoint( iDataCharaX, iDataCharaY );
	g_iImgCharaCrop = ExtractDataSub( DATA_IMG_CHARA_CROP );
	//moveDirect = 2;
	SetDirectPlayer( 2 );
	SetYesNoCrop( ExtractDataSub(DATA_IMG_YESNO_CROP) );
	g_bSaveStop = ExtractDataSub( DATA_SAVE_STOP );
	g_bDefault = ExtractDataSub( DATA_FLAG_DEFAULT );
	g_bOldMap = ExtractDataSub( DATA_FLAG_OLDMAP );
	g_iStep = ExtractDataSub( DATA_STEP );
	g_iMusicNumber = ExtractDataSub( DATA_MUSIC );
	CROP_ENERGY = ExtractDataSub( DATA_CROP );
	CROP_STRENGTH = ExtractDataSub( DATA_CROP +2 );
	CROP_DEFENCE = ExtractDataSub( DATA_CROP +4 );
	CROP_GOLD = ExtractDataSub( DATA_CROP +6 );
	CROP_BOM = ExtractDataSub( DATA_CROP +8 );
	CROP_STFRAME = ExtractDataSub( DATA_CROP +10 );
	CROP_ITEMFRAME = ExtractDataSub( DATA_CROP +12 );
	CROP_MAINFRAME = ExtractDataSub( DATA_CROP +14 );
	g_bDelPlayer = ExtractDataSub( DATA_FLAG_DELP );
	g_iImgClickItem = ExtractDataSub( DATA_IMG_CLICK );
	g_iEffWait = ExtractDataSub( DATA_EFFECT );
	for( i = 0 ; i < 4 ; ++i ) g_iEffCrop[i] = ExtractDataSub( DATA_EFFECT +2+i*2 );
	g_bAnmItem = ExtractDataSub( DATA_ANMITEM );
	//�ϐ��p
	for( i = 0 ; i < 100 ; ++i ) g_iValiable[i] = ExtractDataSub( DATA_VALIABLE +i*2 );

	//�w�i���y�ĊJ
	playAudio( 100 );

	pointer = 380;
	//�}�b�v�f�[�^�W�J
	for( x = 0 ; x < g_iMapWidth ; ++x ){
		for( y = 0 ; y < g_iMapWidth ; ++y ){
			map[x][y] = (short)(unsignedByte(PressData[pointer]) +unsignedByte(PressData[pointer+1]) *0x100);
			pointer += 2;
		}
	}
	for( x = 0 ; x < g_iMapWidth ; ++x ){
		for( y = 0 ; y < g_iMapWidth ; ++y ){
			mapObject[x][y] = (short)(unsignedByte(PressData[pointer]) +unsignedByte(PressData[pointer+1]) *0x100);
			pointer += 2;
		}
	}

	//��ʏ�������
	g_bFadeBlack = true;
}



//////////////////////////////////////////////////
// �������ŕ`��

public void drawStringBold( Graphics g, String szStr, Color colorA, Color colorB, int x, int y )
{
	g.setColor( colorB );
	g.drawString( szStr, x+1, y );
	g.setColor( colorA );
	g.drawString( szStr, x, y );
}

}
