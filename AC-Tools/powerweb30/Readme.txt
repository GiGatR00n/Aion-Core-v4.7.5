===================================
Disclaimer
===================================
License Owner is pr00f
Website:http://makeserv.net/topic/9109-spisok-izmenenii/


PowerWeb 3.0 is not a freeware, so please don't share it world wide.

About this PowerWeb 3.0

In fact is only for Aion 2.7, because of classes and webshop synication development.
It's support only aiondatabase.com items which is just 2.7 and they stopped their development.
Also the displayed tolltips are just for aiondatabase.com, as the russian aiondatabase.net give no website support for syndication.

Other as some other PowerWeb 3.0 which you maybe can find on the web, this one was fixed by me so far.
It works already with "toll", not with "money". Other wrong database query's was also fixed by me.

==========
INSTALL
==========
The install is self explanatory, one sql in the gameserver (ac47_server_gs) and the other in the login server (ac47_server_ls).
The pow.sql is the default standalone database, (you should name it "pow")the custom_db_pow.sql is a little cleaned db and with just a webshop sample.
I would install the custom_db_pow.sql first to see how all works.
The connect config.php is located in powerweb30\protected\config folder


==========
BUGS
==========
1. Password recovery doesn't work, still need to be fixed.

==========
ToDO
==========
1. Statistics rework need to be finished


===========   //   =========================
For those that have nothing better for their webserver, to start a private Aion hosting, PowerWeb 3.0 is more better as 
ServerAion Web 1.2 SAW or other scripts.

Have fun

P.S. if you think that you can do some good modification or fixes to it, it would be nice.
Also to build a webshop I have some samples there just need to be imported in "pow".
