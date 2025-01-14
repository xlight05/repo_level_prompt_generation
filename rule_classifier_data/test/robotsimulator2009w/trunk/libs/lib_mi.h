#ifndef LIB_MI_H
#define LIB_MI_H

#define MI_HEADER1 0xDE
#define MI_HEADER2 0xAD
#define MI_HEADER3 0xBE
#define MI_HEADER4 0xEF

#define MI_COMMAND_LENGTH 9


void mi_send_status(void);
void mi_get_commands(void);
void mi_start(void);
void mi_stop(void);
int mi_event(void);
void putwcrc(unsigned char data);

struct command_packet {

  enum { WHEEL_SPACE, XYTHETA_SPACE } motor_mode;
  union {
    int m100_vel;
    int x_vel;
  };
  union {
    int m220_vel;
    int y_vel;
  };
  union {
    int m340_vel;
    int theta_vel;
  };

  enum { CBATTERY, CBALL, CERROR, CEMERGENCY, CBUSY, CCUSTOM } led_mode;
};


#endif
