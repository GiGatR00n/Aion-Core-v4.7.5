<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Проверка квестов</div>
		<table class="items">
			<tr>
				<th>Персонаж</th>
				<th>Квест</th>
				<th>Кол-во прохождений</th>
			</tr>
		<?php foreach($model as $data): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$data->players->name; ?>"><?php echo $data->players->name ;?></a></td>
				<td><a href="http://aiondatabase.com/quest/<?php echo $data->quest_id ;?>"><?php echo $data->quest_id ;?></a></td>
				<td><?php echo $data->complete_count ;?></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>