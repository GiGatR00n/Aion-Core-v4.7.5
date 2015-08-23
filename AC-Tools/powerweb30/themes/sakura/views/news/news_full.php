<div class="note">
	<div class="note_body h15 img">
		<div class="note_title">
			<?php echo $model->title; ?>
			<?php if (!Yii::app()->user->isGuest AND Yii::app()->user->access_level >= Config::get('access_level_editor')): ?><a class="edit" title="Редактировать" href="<?php echo Yii::app()->homeUrl.'news/edit/'.$model->news_id; ?>"></a><?php endif; ?>
		</div>
		<?php echo $model->short_story; ?>
	</div>
	<div class="note_info">
		<div class="space"><span class="author"></span><?php echo $model->author->name; ?></div>
		<div class="space"><span class="date"></span><?php echo Yii::app()->dateFormatter->format('dd.M.y, HH:mm:ss', $model->date); ?></div>
		<div class="space"><span class="category"></span> <a href="<?php echo Yii::app()->homeUrl.'category/'.$model->category['alt_name']; ?>"><?php echo $model->category['name']; ?></a></div>
	</div>
</div>