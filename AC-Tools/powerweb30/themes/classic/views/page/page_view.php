






<div class="note">
	<div class="note_title">
		<?php echo $model->title; ?>
		<?php if (!Yii::app()->user->isGuest AND Yii::app()->user->access_level >= Config::get('access_level_editor')): ?><a class="edit" title="Edit" href="<?php echo Yii::app()->homeUrl.'page/edit/'.$model->id; ?>"></a><?php endif; ?>
	</div>
	<div class="note_body h15 img">
		<?php echo htmlspecialchars_decode($model->text); ?>
	</div>
</div>