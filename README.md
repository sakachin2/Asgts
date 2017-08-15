#Asgts.README.md 
*************************************************************************
#"Asgts" Android Studio Project Source
*************************************************************************

"Shogi", also known as Japanese Chess, is a strategy board game
and it is said more complex than Chess game.

See Wiki site about Shogi and its rules.
    //en.wikipedia.org/wiki/Shogi

Robo:Bonanza will support you skill up in Shogi.
If there are two devices, you can match through wireless.
  (1).Connect on private LAN with known IP address.
  (2).Peer-to-Peer Bluetooth connection.
  (3).Peer-to-Peer WiFi Direct connection it reaches about a few 10 meters.
      It is available from android 4.
  (4).For device with NFC attachment, 
      Close the part of NFC tag in the range of 10 cm.
      It handover to WiFi Direct or Bluetooth.
  (Warning)
     Support of concurrent use of WiFi and WiFi-Direct depends device type.
     It may cause response delay or disturb telephone function.
     Do not use WiFi direct in that cases.

"Free Board" is for solving Composed-Shogi-Problem(Japanese:Tsumeshogi),
You can get Kifu and Tsumeshogi through Clipboard. 
"Replay Board" is for studying Record-of-Game(Japanese:Kifu).
Supported file format of record of game are kif,ki2,kifu,ki2u,
csa and psn(PlayOK).
You can replay Record-of-Game of Japanese notation by chess-like one.

*************************************************************************
(Japanese)
# "Asgts" Android Studio プロジェクト ソース
*************************************************************************

*** 将棋 ***
新幹線で一局？。
.１つの端末上でローカル対局、
.２つの端末で無線接続で対局ができます。
  (1).職場、学校のLANに接続
  (2).Bluetooth で １対１接続
  (3).Android-4 以降ならWiFiダイレクトでLANなしで近距離接続(数１０ｍまで届きます)、
  (4).NFC機能つきの端末ならNFCタグの部分を10cm以内に近づけるだけで
      WiFiダイレクトまたはBluetoothで接続できます
  (注意)
     WiFi と WiFiダイレクト の混用のサポートはデバイスに依存します
     混用すると応答が非常に遅くなることがあります
     電話機能に影響がある場合もあります、其の場合はWiFiダイレクトは使用しないでください
.ボナンザとの対局で棋力アップ

詰将棋は"フリー将棋盤"で、"棋譜再生"では自戦の感想戦やタイトル戦棋譜の鑑賞も。
棋譜、詰め将棋はクリップボードからも取り込めます
棋譜ファイルは kif,ki2,kifu,ki2u,csa,psn(PlayOK) をサポート。
言語が日本語以外の端末でも日本語の棋譜をチェス形式に似た英語式の表記で見ることができます。

