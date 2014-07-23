package me.andpay.timobileframework.roboguice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import roboguice.inject.InjectFragment;
import roboguice.inject.InjectView;
import roboguice.inject.Nullable;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class ActivityVIewInjectorUtil {

	protected static Class fragmentClass = null;
	protected static Class fragmentManagerClass = null;
	protected static Method fragmentGetViewMethod = null;
	protected static Method fragmentFindFragmentByIdMethod = null;
	protected static Method fragmentFindFragmentByTagMethod = null;

	static {
		try {
			fragmentClass = Class.forName("android.support.v4.app.Fragment");
			fragmentManagerClass = Class
					.forName("android.support.v4.app.FragmentManager");
			fragmentGetViewMethod = fragmentClass.getDeclaredMethod("getView");
			fragmentFindFragmentByIdMethod = fragmentManagerClass.getMethod(
					"findFragmentById", int.class);
			fragmentFindFragmentByTagMethod = fragmentManagerClass.getMethod(
					"findFragmentByTag", Object.class);
		} catch (Throwable ignored) {
		}
	}

	public static void injectorView(Object instance, Activity activity) {
		for (Class<?> c = instance.getClass(); c != Object.class; c = c
				.getSuperclass())
			for (Field field : c.getDeclaredFields()) {
				if (field.isAnnotationPresent(InjectView.class))
					if (Modifier.isStatic(field.getModifiers()))
						throw new UnsupportedOperationException(
								"Views may not be statically injected");
					else if (!View.class.isAssignableFrom(field.getType()))
						throw new UnsupportedOperationException(
								"You may only use @InjectView on fields descended from type View");
					else if (Context.class.isAssignableFrom(field
							.getDeclaringClass())
							&& !Activity.class.isAssignableFrom(field
									.getDeclaringClass()))
						throw new UnsupportedOperationException(
								"You may only use @InjectView in Activity contexts");
					else
						reallyInjectMembers(activity,
								field.getAnnotation(InjectView.class),
								instance, field);

				else if (field.isAnnotationPresent(InjectFragment.class))
					if (Modifier.isStatic(field.getModifiers()))
						throw new UnsupportedOperationException(
								"Fragments may not be statically injected");
					else if (fragmentClass != null
							&& !fragmentClass.isAssignableFrom(field.getType()))
						throw new UnsupportedOperationException(
								"You may only use @InjectFragment on fields descended from type Fragment");
					else if (Context.class.isAssignableFrom(field
							.getDeclaringClass())
							&& !Activity.class.isAssignableFrom(field
									.getDeclaringClass()))
						throw new UnsupportedOperationException(
								"You may only use @InjectFragment in Activity contexts");
					else
						reallyInjectMembers(activity,
								field.getAnnotation(InjectFragment.class),
								instance, field);
			}

	}

	public static void reallyInjectMembers(Object activityOrFragment,
			Annotation annotation, Object instance, Field field) {
		if (annotation instanceof InjectView)
			reallyInjectMemberViews(activityOrFragment, annotation, instance,
					field);
		else
			reallyInjectMemberFragments(activityOrFragment, annotation,
					instance, field);
	}

	/**
	 * This is when the view references are actually evaluated.
	 * 
	 * I don't like all the hacks I had to put into this method. Instance is the
	 * object you're injecting into. ActivityOrFragment is the activity or
	 * fragment that you're injecting views into. Instance must equal
	 * activityOrFragment is activityOrFragment is a fragment, but they may
	 * differ if activityOrFragment is an activity. They should be allowed to
	 * differ so that you can inject views into arbitrary objects, but I don't
	 * know how to determine whether to get the view from the fragment or the
	 * activity for an arbitrary object, so I'm artificially limiting injection
	 * targets to the fragment itself for fragments.
	 * 
	 * @param activityOrFragment
	 *            an activity or fragment
	 * @param field
	 * @param instance2
	 * @param annotation
	 */
	protected static void reallyInjectMemberViews(Object activityOrFragment,
			Annotation annotation, Object instance, Field field) {
		if (instance == null)
			return;

		if (activityOrFragment instanceof Context
				&& !(activityOrFragment instanceof Activity))
			throw new UnsupportedOperationException(
					"Can't inject view into a non-Activity context");

		View view = null;

		try {
			final InjectView injectView = (InjectView) annotation;
			final int id = injectView.value();

			if (id >= 0)
				view = fragmentClass != null
						&& fragmentClass.isInstance(activityOrFragment) ? ((View) fragmentGetViewMethod
						.invoke(activityOrFragment)).findViewById(id)
						: ((Activity) activityOrFragment).findViewById(id);

			else
				view = fragmentClass != null
						&& fragmentClass.isInstance(activityOrFragment) ? ((View) fragmentGetViewMethod
						.invoke(activityOrFragment)).findViewWithTag(injectView
						.tag()) : ((Activity) activityOrFragment).getWindow()
						.getDecorView().findViewWithTag(injectView.tag());

			if (view == null && Nullable.notNullable(field))
				throw new NullPointerException(
						String.format(
								"Can't inject null value into %s.%s when field is not @Nullable",
								field.getDeclaringClass(), field.getName()));

			field.setAccessible(true);
			field.set(instance, view);

		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);

		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);

		} catch (IllegalArgumentException f) {
			throw new IllegalArgumentException(String.format(
					"Can't assign %s value %s to %s field %s",
					view != null ? view.getClass() : "(null)", view, field
							.getType(), field.getName()), f);
		}
	}

	/**
	 * This is when the view references are actually evaluated.
	 * 
	 * @param activityOrFragment
	 *            an activity or fragment
	 * @param field
	 * @param instance2
	 * @param annotation
	 */
	protected static void reallyInjectMemberFragments(
			Object activityOrFragment, Annotation annotation, Object instance,
			Field field) {
		if (instance == null)
			return;

		if (activityOrFragment instanceof Context
				&& !(activityOrFragment instanceof Activity))
			throw new UnsupportedOperationException(
					"Can't inject fragment into a non-Activity context");

		Object fragment = null;

		try {
			final InjectFragment injectFragment = (InjectFragment) annotation;
			final int id = injectFragment.value();

			if (id >= 0)
				fragment = fragmentFindFragmentByIdMethod.invoke(
						((FragmentActivity) activityOrFragment)
								.getSupportFragmentManager(), id);

			else
				fragment = fragmentFindFragmentByTagMethod.invoke(
						((FragmentActivity) activityOrFragment)
								.getSupportFragmentManager(), injectFragment
								.tag());

			if (fragment == null && Nullable.notNullable(field))
				throw new NullPointerException(
						String.format(
								"Can't inject null value into %s.%s when field is not @Nullable",
								field.getDeclaringClass(), field.getName()));

			field.setAccessible(true);
			field.set(instance, fragment);

		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);

		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);

		} catch (IllegalArgumentException f) {
			throw new IllegalArgumentException(String.format(
					"Can't assign %s value %s to %s field %s",
					fragment != null ? fragment.getClass() : "(null)",
					fragment, field.getType(), field.getName()), f);
		}
	}

}
