<div class="note mb15">
	<div class="note_setting">
		<div class="note_title">Управление контентом</div>
		<table class="table_setting">
			<tr>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/news.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>news/list/"><p>Список новостей</p>
					Управление новостями</a>
				</td>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/write.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>news/add"><p>Добавление новостей</p>
					Добавление новости</a>
				</td>
			</tr>
			<tr>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/documents.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>page/list"><p>Список страниц</p>
					Управление страницами</a>
				</td>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/page.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>page/add"><p>Добавление страниц</p>
					Добавление страницы</a>
				</td>
			</tr>
			<!--<tr>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/calender2.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin"><p>Список ивентов</p>
					Управление ивентами</a>
				</td>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/calender.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin"><p>Добавление ивентов</p>
					Добавление ивента</a>
				</td>
			</tr>-->
			<tr>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/category.png" /></td>
				<td><a href="<?php echo Yii::app()->homeUrl; ?>admin/news/categories/"><p>Категории новостей</p>
					Управление категориями новостей
				</td></a>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
</div>


<div class="note mb15">
	<div class="note_setting">
		<div class="note_title">Отчеты</div>
		<table class="table_setting">
			<tr>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/billing.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/log/billing"><p>Пополнение баланса</p>
					Операции пополнения баланса</a>
				</td>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/basket.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/log/webshop"><p>Покупка вещей</p>
					Операции покупок вещей в магазине</a>
				</td>
			</tr>
			<tr>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/prize.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/log/membership"><p>Покупка привилегий</p>
					Операции покупок привилегий</a>
				</td>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/direction.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/log/points"><p>Передача поинтов</p>
					Операции передачи поинтов между аккаунтами</a>
				</td>
			</tr>
		</table>
	</div>
</div>


<div class="note mb15">
	<div class="note_setting">	
		<div class="note_title">Управление игроками</div>
		<table class="table_setting">
			<tr>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/user.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/account/list/"><p>Список аккаунтов</p>
					Управление аккаунтами</a>
				</td>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/contact.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/player/list/"><p>Список персонажей</p>
					Управление персонажами</a>
				</td>
			</tr>
			<tr>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/bookmark.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/legion/list/"><p>Список легионов</p>
					Управление легионами</a>
				</td>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/card.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/log/referals/"><p>Список рефералов</p>
					Управление рефералами</a>
				</td>
			</tr>
			<tr>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/mail.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/mail/"><p>Отправка почты</p>
					Отправка почты игрокам</a>
				</td>
			</tr>
		</table>
	</div>
</div>


<div class="note mb15">
	<div class="note_setting">	
		<div class="note_title">Проверка</div>
		<table class="table_setting">
			<tr>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/magic.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/check/items/"><p>Проверка сдвоенных вещей</p>
					Проверка сдвоенных вещей с помощью l2phx</a>
				</td>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/chat.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/check/quest/"><p>Проверка квестов</p>
					Проверка многократного похождения квестов</a>
				</td>
			</tr>
			<tr>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/brushes.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/check/expire/"><p>Очистка истекших привилегий</p>
					Сброс истекщих привилегий у аккаунта</a>
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>		
	</div>
</div>


<div class="note mb15">
	<div class="note_setting">
		<div class="note_title">Магазин</div>
		<table class="table_setting">
			<tr>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/gift.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/webshop/add/"><p>Добавление вещей</p>
					Добавление вещей в магазин</a>
				</td>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/cabinet.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/webshop/categories/"><p>Категории магазина</p>
					Управление категориями магазина</a>
				</td>
			</tr>
			<tr>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/shop.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/webshop/list/"><p>Список добавленных вещей</p>
					Редактирование добавленных вещей в магазин</a>
				</td>
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/membership.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/webshop/membership/"><p>Добавление привилегий</p>
					Добавление привилегий для аккаунта</a>
				</td>
			</tr>
		</table>
	</div>
</div>


<div class="note mb15">
	<div class="note_setting">
		<div class="note_title">Голосования</div>
		<table class="table_setting">
			<tr>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/monitoring.png" /></td>
				<td width="365px">
					<p>Статистика голосований:</p>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/vote/aiontop/">aion-top.info</a>,
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/vote/l2top/">aion.l2top.ru</a>,
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/vote/mmotop/">aion.mmotop.ru</a>,
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/vote/gtop/">gtop100.com</a>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/vote/gamesites/">gamesites200.com</a>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/vote/xtremetop/">xtremetop100.com</a>
				</td>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/clock.png" /></td>
				<td width="365px">
					<p>Пересчет голосов:</p>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/vote/checkaiontop/">[aion-top.info]</a>,
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/vote/checkl2top/">[aion.l2top.ru]</a>,
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/vote/checkmmotop/">[aion.mmotop.ru]</a>
				</td>
			</tr>
		</table>	
	</div>
</div>


<div class="note mb15">
	<div class="note_setting">
		<div class="note_title">Настройки</div>
		<table class="table_setting">
			<tr>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/cash.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/pay/"><p>Настройки платежных систем</p>
					Данные для подключения платежных систем</a>
				</td>
				<td width="60px"><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/statistics.png" /></td>
				<td width="365px">
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/vote/config/"><p>Настройки рейтингов</p>
					Данные для подключения рейтингов</a>
				</td>
			</tr>
			<tr>
				<!--<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/monitor.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin"><p>Список серверов</p>
					Управление списком серверов</a>
				</td>-->
				<td><img src="<?php echo Yii::app()->homeUrl; ?>images/admin/setting.png" /></td>
				<td>
					<a href="<?php echo Yii::app()->homeUrl; ?>admin/settings/"><p>Общие настройки</p>
					Настройка обвязки и ее компонентов</a>
				</td>
			</tr>
		</table>
	</div>
</div>


<div class="note">
	<div class="note_body">
		<div class="note_title">Общая информация</div>
		<table class="items">
			<tr>
				<td width="365px">Версия скрипта</td>
				<td><?php echo Yii::app()->params->version; ?></td>
			</tr>
			<tr>
				<td>Версия PHP</td>
				<td><?php echo PHP_VERSION; ?></td>
			</tr>
			<tr>
				<td>Новостей</td>
				<td><?php echo Status::news(); ?></td>
			</tr>
			<tr>
				<td>Аккаунтов</td>
				<td><?php echo Status::accounts(); ?></td>
			</tr>
			<tr>
				<td>Персонажей</td>
				<td><?php echo Status::players(); ?></td>
			</tr>
			<tr>
				<td>Легионов</td>
				<td><?php echo Status::legions(); ?></td>
			</tr>
			<tr>
				<td>ГМ аккаунтов</td>
				<td><?php echo Status::gms(); ?></td>
			</tr>
		</table>
	</div>
</div>