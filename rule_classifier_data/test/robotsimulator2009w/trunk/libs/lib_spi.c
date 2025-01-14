/*
 *  lib_spi.c
 *  
 *
 *  Created by Michael Sokolsky on 4/11/08.
 *  Last Revision: 01/07/08
 *
 */

#include "compiler.h"
#include "AT91SAM7S256.h"
#include "lib_AT91SAM7S256.h"
#include "lib_spi.h"
#include "lib_critical.h"
#include <stdio.h>

event_s spi_event_s = {
  spi_init,
  NULL,
  0
};

/*
 *  Initialize the SPI unit
 */
int spi_init() {
  
  AT91PS_SPI spi = AT91C_BASE_SPI;

  // Enable the SPI clock in the PMC
  *AT91C_PMC_PCER = 1 << AT91C_ID_SPI;
  
  // Set outputs to correct pins on the device
  *AT91C_PIOA_ASR = SPI_A_PINS;
  *AT91C_PIOA_BSR = SPI_B_PINS;
  *AT91C_PIOA_PDR = SPI_A_PINS | SPI_B_PINS;
  
  
  AT91F_AIC_ConfigureIt( AT91C_BASE_AIC, 
                         AT91C_ID_SPI,
                         SPI_INTERRUPT_PRIORITY,
                         AT91C_AIC_SRCTYPE_INT_HIGH_LEVEL,
                         (void*)spi_isr );
  AT91F_AIC_EnableIt( AT91C_BASE_AIC, AT91C_ID_SPI );
  
  /*  SPI Mode Register
   *
   *  Master mode
   *  Variable peripheral select
   *  Using external 4:16 decoder
   *  Insert delay between chip select line changes
   */
  spi->SPI_MR = ( AT91C_SPI_MSTR | 
                  AT91C_SPI_PS_VARIABLE |
                  AT91C_SPI_PCSDEC |
                  DELAY_BETWEEN_CS );
  
  // Setup chip communication settings
  spi->SPI_CSR[0] = SPI_CSR0_SETTINGS;
  spi->SPI_CSR[1] = SPI_CSR1_SETTINGS;
  spi->SPI_CSR[2] = SPI_CSR2_SETTINGS;
  spi->SPI_CSR[3] = SPI_CSR3_SETTINGS;
  
  // Enable SPI
  spi->SPI_CR = AT91C_SPI_SPIEN;
  return 0;
}


/*
 *  Add a new packet to the SPI transmission queue
 */
void spi_send_packet( struct spi_packet *packet ) {
  
  AT91PS_SPI spi = AT91C_BASE_SPI;
  int i;
  unsigned int pcs = packet->device_id << 16;
  
  /* Add Peripheral ID to each word in packet
   *
   * This has a minor implication to clients, in that the data they
   * put in the data_to_write array does get modified to include the
   * PDC metadata for the transfer.  There don't seem to be any
   * obvious repurcussions for our application, however it is something
   * to keep in mind.
   */
  for( i = 0; i < packet->num_words; i++ ) {
    *( packet->data_to_write + i ) |= pcs;
  }
  // Add LASTXFER bit to last word in packet
  *(packet->data_to_write + packet->num_words - 1) |= 0x01000000;
  
  spi->SPI_PTCR = AT91C_PDC_RXTDIS | AT91C_PDC_TXTDIS;
  
  // This is the last packet in the list.
  packet->next_packet = NULL;
  // If this isn't also the first packet in the list, add it to 
  // the end
  if( spi_data_head != NULL ) {
    spi_data_head->next_packet = packet;
    spi_data_head = packet;
    spi->SPI_PTCR = AT91C_PDC_RXTEN | AT91C_PDC_TXTEN;
  }
  // If this is the first packet in the list, set up the PDC and
  // start the transfer
  else {
    spi_data_tail = spi_data_head = packet;
    spi->SPI_TPR = (unsigned int) spi_data_tail->data_to_write;
    spi->SPI_TCR = spi_data_tail->num_words;
    spi->SPI_PTCR = AT91C_PDC_TXTEN;
    spi->SPI_IER = AT91C_SPI_TXBUFE;
    // If read_data is NULL, we're ignoring received data
    if( spi_data_tail->read_data != NULL )
    {
      spi->SPI_RPR = (unsigned int) spi_data_tail->read_data;
      spi->SPI_RCR = spi_data_tail->num_words;
      spi->SPI_PTCR = AT91C_PDC_RXTEN;
      spi->SPI_IER = AT91C_SPI_RXBUFF;
    }
  }
  
}


/*
 *  SPI Interrupt routine
 *  MAKE SURE THIS IS RUN IN ARM MODE AND IS IN RAM!!!
 *  GCC will likely require different handling than IAR
 *  to accomplish this.
 */
ARM_CODE RAMFUNC void spi_isr() {
  
  AT91PS_SPI spi = AT91C_BASE_SPI;
  unsigned int old_reg;
  
  /* Check if both ENDRX and ENDTX flags are set
   * 
   * The transmission isn't done until both are set, as ENDTX will occur
   * at the beginning of the final packet, ENDRX won't occur until after
   * the end of the final packet.  Ignore further END
   *
   * CHECK THAT FLAGS ARE CLEARED WHEN WRITING TO PDC COUNTER REGISTERS
   */
  if(spi->SPI_SR & AT91C_SPI_TXBUFE) 
    spi->SPI_IDR = AT91C_SPI_TXBUFE; 
  if(spi->SPI_SR & AT91C_SPI_RXBUFF) 
    spi->SPI_IDR = AT91C_SPI_RXBUFF; 
  
  if(spi_data_tail == NULL)
    return;
  
  // Disable PDC transfers just to be safe
  old_reg = spi->SPI_PTSR;
  spi->SPI_PTCR = AT91C_PDC_RXTDIS | AT91C_PDC_TXTDIS;
  
  if((spi->SPI_SR & AT91C_SPI_RXBUFF) && (spi->SPI_SR & AT91C_SPI_TXBUFE)) {
    // Let the packet's client know we're done
    spi_data_tail->finished++;
    /* If there is another packet in the list, prep it and start transfer
     * 
     * This is the same code as in spi_send_packet, but this is run in
     * ARM mode and is in RAM, so it needs to be compilied seperately
     * and can't be put into a function easily AFAIK.
     */
    if( spi_data_tail->next_packet != NULL ) {
      spi_data_tail = spi_data_tail->next_packet;
      spi->SPI_TPR = (unsigned int)spi_data_tail->data_to_write;
      spi->SPI_TCR = spi_data_tail->num_words;
      spi->SPI_PTCR = AT91C_PDC_TXTEN;
      spi->SPI_IER = AT91C_SPI_TXBUFE;
      // If read_data is NULL, we're ignoring received data
      if( spi_data_tail->read_data != NULL )
      {
        spi->SPI_RPR = (unsigned int)spi_data_tail->read_data;
        spi->SPI_RCR = spi_data_tail->num_words;
        spi->SPI_PTCR = AT91C_PDC_RXTEN;
        spi->SPI_IER = AT91C_SPI_RXBUFF;
      }
    }
    // We're done for now!
    else {
      spi_data_head = NULL;
      spi_data_tail = NULL;
    }
  } 
  else {
    spi->SPI_PTCR = old_reg;
  }
}

void spi_set_word_size(unsigned int cs, unsigned int wordsize)
{
  AT91PS_SPI spi = AT91C_BASE_SPI;
  unsigned int settings = spi->SPI_CSR[cs];

  spi->SPI_CSR[cs] = (settings & (~AT91C_SPI_BITS)) | (wordsize+8);
}
