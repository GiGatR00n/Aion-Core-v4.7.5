






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Checking quest status...
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>Character</th>
				<th>Quest</th>
				<th>Qty passed</th>
			</tr>
		<?php foreach($model as $data): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$data->players->name; ?>"><?php echo $data->players->name ;?></a></td>
				<td><a href="http://www.aiondatabase.com/quest/<?php echo $data->quest_id ;?>"><?php echo $data->quest_id ;?></a></td>
				<td><?php echo $data->complete_count ;?></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>